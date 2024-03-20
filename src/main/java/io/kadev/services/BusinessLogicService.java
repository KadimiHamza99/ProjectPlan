package io.kadev.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


import io.kadev.dto.ProductResponseDto;
import io.kadev.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import io.kadev.dto.ProductRequestDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.exceptions.BusinessLogicException;
import io.kadev.exceptions.ProductNotFoundException;
import io.kadev.exceptions.ProjectNotFoundException;
import io.kadev.models.Project;
import io.kadev.repositories.ProductRepository;
import io.kadev.repositories.ProjectRepository;
import io.kadev.utils.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class BusinessLogicService implements BusinessLogicInterface {
	
	private ProductRepository produitRepo;
	private ProjectRepository projectRepo;
	private ValueMapper mapper;
	
	
	/*
	 * This Method will create a new project
	 * */
	public ProjectResponseDto createNewProject(ProjectRequestDto projectRequestDto) throws BusinessLogicException {
		ProjectResponseDto projectResponseDto;
		try {
			Project project = mapper.toProjectEntity(projectRequestDto);
			Project projectResult = projectRepo.save(project);
			projectResponseDto = mapper.toProjectResponseDto(projectResult);
		}
		catch(BusinessLogicException e) {
			throw new BusinessLogicException("Exception occured while saving Project in the database");
		}
		return projectResponseDto;
	}
	/*
	 * This method will create a new product for the project
	 * */
	public ProductResponseDto createNewProduit(ProductRequestDto productRequestDto) throws BusinessLogicException {
		ProductResponseDto productResponseDto;
		try {
			Project project = projectRepo.findById(productRequestDto.getProject_id()).get();
			Product product = mapper.toProduitEntity(productRequestDto,project);
			project.getProducts().add(product);
			project.setQuantiteTotal(project.getQuantiteTotal()+ product.getQuantite());
			project.setChiffreAffaireTotal(project.getChiffreAffaireTotal()+ product.getQuantite()* product.getPrixVenteUnitaire());
			Product productResult = produitRepo.save(product);
			productResponseDto = mapper.toProduitResponseDto(productResult);
		}
		catch(BusinessLogicException e) {
			throw new BusinessLogicException("Exception occured while saving Product in the database");
		}
		return productResponseDto;
	}
	/*
	 * This method will do some operations and calcul some metrics to see is the product is rentable
	 * */
	public ProjectResponseDto calculMetrics(Long projectId) throws BusinessLogicException {
		ProjectResponseDto projectResponseDto;
		AtomicReference<Double> resultatExploitation = new AtomicReference<>((double) 0);
		try {
			Project project = projectRepo.findById(projectId).orElseThrow(()->new ProjectNotFoundException("Exception occured while fetching the project from the DB !"));
			project.getProducts().stream().forEach(produit->{
				int quantite = produit.getQuantite();
				double prixVenteUnitaire = produit.getPrixVenteUnitaire();
				double coutVariableUnitaire = produit.getCoutVariableUnitaire();
				double coutFixesDirect = produit.getCoutsFixesDirects();
				double chargesFixesCommunes = project.getChargesFixesCommunes();
				double chiffreAffaireProduit = quantite * prixVenteUnitaire;
				double margeCoutsDirect = chiffreAffaireProduit - quantite*coutVariableUnitaire - coutFixesDirect;
				double partCA = chiffreAffaireProduit/project.getChiffreAffaireTotal();
				double repartitionProrata = partCA*chargesFixesCommunes;
				double margeCoutComplet = margeCoutsDirect - repartitionProrata;
				produit.setChiffreAffaire(chiffreAffaireProduit);
				produit.setMargeCoutsDirects(margeCoutsDirect);
				produit.setPartChiffreAffaire(partCA);
				produit.setRepartitionCFCProrataCA(repartitionProrata);
				produit.setMargeCoutsComplets(margeCoutComplet);
				resultatExploitation.updateAndGet(v -> new Double(
						(v + margeCoutComplet)));
				double tauxMargeCoutsVariables = (chiffreAffaireProduit-quantite*coutVariableUnitaire)/chiffreAffaireProduit;
				double seuilRentabilite = (coutFixesDirect+repartitionProrata)/tauxMargeCoutsVariables;
				produit.setSeuilRentabilite(seuilRentabilite);
				//Nombre de ventes necessaires est plus grand que l'objectif = projet deficitaire
				double nombreVentesNecessaires = seuilRentabilite/prixVenteUnitaire;
				produit.setNombreVentesNecessaires(nombreVentesNecessaires);
				produit.setMargeCoutsVariables(tauxMargeCoutsVariables);
				produit.setPointMort(nombreVentesNecessaires/ produit.getObjectifParJour());
				int objectifParAnnee = produit.getObjectifParJour()*200;
				double prixVenteOptimal = ((repartitionProrata+coutFixesDirect)/ objectifParAnnee) + produit.getCoutVariableUnitaire();
				produit.setPrixVenteOptimal(prixVenteOptimal);
			});
			project.setResultatsExploitation(resultatExploitation.get());
			Project projectResult = projectRepo.save(project);
			projectResponseDto = mapper.toProjectResponseDto(projectResult);
		}
		catch(BusinessLogicException e) {
			throw new BusinessLogicException("Exception occured while updating Project object !");
		}
		log.info(projectResponseDto.toString());
		return projectResponseDto;
	}
	/*
	 * Update the name or couts fixes communes for a project 
	 * */
	@Override
	public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id) throws BusinessLogicException {
		ProjectResponseDto projectResponse;
		try {
			Project projectToUpdate = projectRepo.findById(id).orElseThrow(()->new ProjectNotFoundException("Project Not found"));
			projectToUpdate.setNom(projectRequestDto.getNom());
			projectToUpdate.setChargesFixesCommunes(projectRequestDto.getChargeFixesCommunes());
			projectRepo.save(projectToUpdate);
			projectResponse = mapper.toProjectResponseDto(projectToUpdate);
			return projectResponse;
		}catch(Exception e) {
			throw new BusinessLogicException("An Exception occured while updating the project with id : "+id);
		}
	}
	/*
	 * Update Product
	 * */
	@Override
	public ProductResponseDto updateProduit(ProductRequestDto productRequestDto, Long id) throws BusinessLogicException {
		ProductResponseDto produitResponse;
		try {
			log.info("BusinessLogicService:updateProduit Execution started");
			Product productToUpdate = produitRepo.findById(id).orElseThrow(()->new ProductNotFoundException("Product Not found"));
			productToUpdate.setName(productRequestDto.getName());
			productToUpdate.setCoutsFixesDirects(productRequestDto.getCoutsFixesDirects());
			productToUpdate.setCoutVariableUnitaire(productRequestDto.getCoutVariableUnitaire());
			productToUpdate.setPrixVenteUnitaire(productRequestDto.getPrixVenteUnitaire());
			productToUpdate.setQuantite(productRequestDto.getQuantite());
			produitRepo.save(productToUpdate);
			produitResponse = mapper.toProduitResponseDto(productToUpdate);
			return produitResponse;
		}catch(Exception e) {
			throw new BusinessLogicException("An Exception occured while updating the produit with id : "+id);
		}
	}
	/*
	 * Delete Project
	 * */
	@Override
	public boolean deleteProject(Long id) throws BusinessLogicException {
		try {
			projectRepo.deleteById(id);
			return true;
		}catch(Exception e) {
			throw new BusinessLogicException("Exception occured while deleting the project with the id: "+id+" from DB");
		}
	}
	/*
	 * Delete Product
	 * */
	@Override
	public boolean deleteProduit(Long id) throws BusinessLogicException {
		try {
			Product product = produitRepo.findById(id).orElseThrow(()->new ProductNotFoundException("Product with id : "+id+"not found in the DB"));
			product.getProject().getProducts().remove(product);
			product.setProject(null);
			produitRepo.delete(product);
			return true;
		}catch(Exception e) {
			throw new BusinessLogicException("Exception occured while deleting the produit with the id: "+id+" from DB");
		}
	}
	@Override
	public boolean deleteProduit(Long projectId, Long productId) throws BusinessLogicException {
		try {
			Product product = produitRepo.findByIdAndProjectId(projectId, productId).orElseThrow(()->new ProductNotFoundException("Product with id : "+productId+"not found in the DB"));
			product.getProject().getProducts().remove(product);
			product.setProject(null);
			produitRepo.delete(product);
			return true;
		}catch(Exception e) {
			throw new BusinessLogicException("Exception occured while deleting the produit with the id: "+productId+" from DB");
		}
	}
	/*
	 * Fetch all projects
	 * */
	@Override
	public Collection<ProjectResponseDto> getAllProjects() {
		List<ProjectResponseDto> projectsResponses = null;
		try {
			List<Project> projects = projectRepo.findAll();
			if(projects.isEmpty()) {
				projectsResponses = Collections.emptyList(); 
			}else {
				projectsResponses = projects.stream()
											.map(mapper::toProjectResponseDto)
											.collect(Collectors.toList());
			}
			return projectsResponses;
		}catch(Exception e) {
			throw new BusinessLogicException("An exception occured while getting projects");
		}
	}
	/*
	 * Fetch All the products for one project
	 * */
	@Override
	public Collection<ProductResponseDto> getAllProjectProduits(Long projectId) {
		List<ProductResponseDto> produitsResponses = null;
		Project project;
		try {
			project = projectRepo.findById(projectId).orElseThrow(()->new ProjectNotFoundException("Project not found"));
			List<Product> products = project.getProducts();
			if(products.isEmpty()) {
				produitsResponses = Collections.emptyList();
			}else {
				produitsResponses = products.stream()
											.map(mapper::toProduitResponseDto)
											.collect(Collectors.toList());
			}
			return produitsResponses;
		}catch(Exception e) {
			throw new BusinessLogicException("An exception occured while getting produits");
		}
	}
	/*
	 * Get a project by id
	 * */
	@Override
	public ProjectResponseDto getProject(Long id) {
		try {
			Project project = projectRepo.findById(id).orElseThrow(()->new ProjectNotFoundException("An Exception has occured while fetching the project from the DB"));
			ProjectResponseDto response = mapper.toProjectResponseDto(project);
			return response;
		}catch(Exception e) {
			throw new BusinessLogicException("An exception occured while getting the project from the DB");
		}
	}
	/*
	 * get a product by id
	 * */
	@Override
	public ProductResponseDto getProduit(Long id) {
		try {
			Product product = produitRepo.findById(id).orElseThrow(()->new ProductNotFoundException("An Exception has occured while fetching the project from the DB"));
			ProductResponseDto response = mapper.toProduitResponseDto(product);
			return response;
		}catch(Exception e) {
			throw new BusinessLogicException("An exception occured while getting the produit from the DB");
		}
	}
	
}
