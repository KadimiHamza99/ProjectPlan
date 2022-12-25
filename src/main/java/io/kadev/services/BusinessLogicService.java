package io.kadev.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProduitResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.exceptions.BusinessLogicException;
import io.kadev.exceptions.ProduitNotFoundException;
import io.kadev.exceptions.ProjectNotFoundException;
import io.kadev.models.Produit;
import io.kadev.models.Project;
import io.kadev.repositories.ProduitRepository;
import io.kadev.repositories.ProjectRepository;
import io.kadev.utils.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class BusinessLogicService implements BusinessLogicInterface {
	
	private ProduitRepository produitRepo;
	private ProjectRepository projectRepo;
	
	
	/*
	 * This Method will create a new project
	 * */
	public ProjectResponseDto createNewProject(ProjectRequestDto projectRequestDto) throws BusinessLogicException {
		ProjectResponseDto projectResponseDto;
		try {	
			log.info("BusinessLogicService:createNewProject execution started");
			Project project = ValueMapper.toProjectEntity(projectRequestDto);
			Project projectResult = projectRepo.save(project);
			projectResponseDto = ValueMapper.toProjectResponseDto(projectResult);
			log.debug("BusinessLogicService:createNewProject value received from DB {}",ValueMapper.jsonAsString(projectRequestDto));
		}
		catch(BusinessLogicException e) {
			log.error("BusinessLogicService:createNewProject Error occured while saving object into the database, message {}",e.getMessage());
			throw new BusinessLogicException("Exception occured while saving Project in the database");
		}
		log.info("BusinessLogicService:createNewProject execution ended");
		return projectResponseDto;
	}
	/*
	 * This method will create a new product for the project
	 * */
	public ProduitResponseDto createNewProduit(ProduitRequestDto produitRequestDto) throws BusinessLogicException {
		ProduitResponseDto produitResponseDto;
		try {	
			log.info("BusinessLogicService:createNewProduit execution started");
			Project project = projectRepo.findById(produitRequestDto.getProject_id()).get();
			Produit produit = ValueMapper.toProduitEntity(produitRequestDto,project);
			project.getProduits().add(produit);
			project.setQuantiteTotal(project.getQuantiteTotal()+produit.getQuantite());
			project.setChiffreAffaireTotal(project.getChiffreAffaireTotal()+produit.getQuantite()*produit.getPrixVenteUnitaire());
			Produit produitResult = produitRepo.save(produit);
			produitResponseDto = ValueMapper.toProduitResponseDto(produitResult);
			log.debug("BusinessLogicService:createNewProduit value received from DB {}",ValueMapper.jsonAsString(produitRequestDto));
		}
		catch(BusinessLogicException e) {
			log.error("BusinessLogicService:createNewProduit Error occured while saving object into the database, message {}",e.getMessage());
			throw new BusinessLogicException("Exception occured while saving Produit in the database");
		}
		log.info("BusinessLogicService:createNewProduit execution ended");
		return produitResponseDto;
	}
	/*
	 * This method will do some operations and calcul some metrics to see is the product is rentable
	 * */
	public ProjectResponseDto calculMetrics(Long projectId) throws BusinessLogicException {
		ProjectResponseDto projectResponseDto;
		try {
			log.info("BusinessLogicService:calculMetrics execution started");
			Project project = projectRepo.findById(projectId).orElseThrow(()->new ProjectNotFoundException("Exception occured while fetching the project from the DB !"));
			project.getProduits().stream().forEach(produit->{
				int quantite = produit.getQuantite();
				double chiffreAffaire = quantite*produit.getPrixVenteUnitaire();
				double margeCoutsVariables = produit.getQuantite()*(produit.getPrixVenteUnitaire()-produit.getCoutVariableUnitaire());
				double margeCoutsDirects = margeCoutsVariables-produit.getCoutsFixesDirects();
				double partChiffreAffaire = (double) chiffreAffaire / produit.getProject().getChiffreAffaireTotal();
				double repartitionProrata = partChiffreAffaire*produit.getProject().getCoutsFixesCommunes();
				double margeCoutsComplets = margeCoutsDirects-repartitionProrata;
				double seuilRentabilite = (produit.getCoutsFixesDirects()+repartitionProrata)/(margeCoutsVariables/chiffreAffaire);
				int nombreVentesNecessaires = (int) (seuilRentabilite/produit.getPrixVenteUnitaire());
				double pointMort = seuilRentabilite/(produit.getNombreVenteEstimeParSemaine()*produit.getPrixVenteUnitaire());
				produit.setChiffreAffaire(chiffreAffaire);
				produit.setMargeCoutsVariables(margeCoutsVariables);
				produit.setMargeCoutsDirects(margeCoutsDirects);
				produit.setPartChiffreAffaire(partChiffreAffaire);
				produit.setRepartitionProrata(repartitionProrata);
				produit.setMargeCoutsComplets(margeCoutsComplets);
				produit.setSeuilRentabilite(seuilRentabilite);
				/*
				 * Le nombre de vente necessaire pour que notre projet soit rentable
				 * */
				produit.setNombreVentesNecessaires(nombreVentesNecessaires);
				/*
				 * Le nombre de semaines pour que notre projet soit rentable
				 * */
				produit.setPointMort(pointMort);
				project.setResultatsExploitation(project.getResultatsExploitation()+margeCoutsComplets);
			});
			Project projectResult = projectRepo.save(project);
			projectResponseDto = ValueMapper.toProjectResponseDto(projectResult);
			log.debug("BusinessLogicService:calculMetrics value received from DB {}",projectResult);
		}
		catch(BusinessLogicException e) {
			log.error("BusinessLogicService:calculMetrics Error occured whil e saving object into the database, message {}",e.getMessage());
			throw new BusinessLogicException("Exception occured while updating Project object !");
		}
		log.info("BusinessLogicService:calculMetrics execution ended");
		return projectResponseDto;
	}
	/*
	 * Update the name or couts fixes communes for a project 
	 * */
	@Override
	public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id) throws BusinessLogicException {
		ProjectResponseDto projectResponse;
		try {
			log.info("BusinessLogicService:updateProject Execution started");
			Project projectToUpdate = projectRepo.findById(id).orElseThrow(()->new ProjectNotFoundException("Project Not found"));
			projectToUpdate.setNom(projectRequestDto.getNom());
			projectToUpdate.setCoutsFixesCommunes(projectRequestDto.getCoutsFixesCommunes());
			projectRepo.save(projectToUpdate);
			log.info("BusinessLogicService:updateProject updated project have been saved");
			projectResponse = ValueMapper.toProjectResponseDto(projectToUpdate);
			return projectResponse;
		}catch(Exception e) {
			log.error("BusinessLogicService:updateProject An error occured while updating the project");
			throw new BusinessLogicException("An Exception occured while updating the project with id : "+id);
		}
	}
	/*
	 * Update Product
	 * */
	@Override
	public ProduitResponseDto updateProduit(ProduitRequestDto produitRequestDto, Long id) throws BusinessLogicException {
		ProduitResponseDto produitResponse;
		try {
			log.info("BusinessLogicService:updateProduit Execution started");
			Produit produitToUpdate = produitRepo.findById(id).orElseThrow(()->new ProduitNotFoundException("Produit Not found"));
			produitToUpdate.setName(produitRequestDto.getName());
			produitToUpdate.setCoutsFixesDirects(produitRequestDto.getCoutsFixesDirects());
			produitToUpdate.setCoutVariableUnitaire(produitRequestDto.getCoutVariableUnitaire());
			produitToUpdate.setNombreVenteEstimeParSemaine(produitRequestDto.getNombreVenteEstimeParSemaine());
			produitToUpdate.setPrixVenteUnitaire(produitRequestDto.getPrixVenteUnitaire());
			produitToUpdate.setQuantite(produitRequestDto.getQuantite());
			produitRepo.save(produitToUpdate);
			log.info("BusinessLogicService:updateProduit updated produit have been saved");
			produitResponse = ValueMapper.toProduitResponseDto(produitToUpdate);
			return produitResponse;
		}catch(Exception e) {
			log.error("BusinessLogicService:updateProduit An error occured while updating the produit");
			throw new BusinessLogicException("An Exception occured while updating the produit with id : "+id);
		}
	}
	/*
	 * Delete Project
	 * */
	@Override
	public boolean deleteProject(Long id) throws BusinessLogicException {
		try {
			log.info("BusinessLogicService:deleteProject Execution started");
			projectRepo.deleteById(id);
			log.info("BusinessLogicService:deleteProject Project deleted from DB");
			return true;
		}catch(Exception e) {
			log.error("BusinessLogicService:deleteProject An error occured while deleting project");
			throw new BusinessLogicException("Exception occured while deleting the project with the id: "+id+" from DB");
		}
	}
	/*
	 * Delete Product
	 * */
	@Override
	public boolean deleteProduit(Long id) throws BusinessLogicException {
		try {
			log.info("BusinessLogicService:deleteProduit Execution started");
			Produit produit = produitRepo.findById(id).orElseThrow(()->new ProduitNotFoundException("Produit with id : "+id+"not found in the DB"));
			produit.getProject().getProduits().remove(produit);
			produit.setProject(null);	
			produitRepo.delete(produit);
			log.info("BusinessLogicService:deleteProduit Produit deleted from DB");
			return true;
		}catch(Exception e) {
			log.error("BusinessLogicService:deleteProduit An error occured while deleting produit");
			throw new BusinessLogicException("Exception occured while deleting the produit with the id: "+id+" from DB");
		}
	}
	/*
	 * Fetch all projects
	 * */
	@Override
	public Collection<ProjectResponseDto> getAllProjects() {
		List<ProjectResponseDto> projectsResponses = null;
		try {
			log.info("BusinessLogicService:getAllProjects Execution started");
			List<Project> projects = projectRepo.findAll();
			if(projects.isEmpty()) {
				log.info("BusinessLogicService:getAllProjects there is no projects on the DB");
				projectsResponses = Collections.emptyList(); 
			}else {
				log.info("BusinessLogicService:getAllProjects Convert each project entity into projectResponseDto");
				projectsResponses = projects.stream()
											.map(ValueMapper::toProjectResponseDto)
											.collect(Collectors.toList());
			}
			log.info("BusinessLogicService:getAllProjects Execution finished successfully");
			return projectsResponses;
		}catch(Exception e) {
			log.error("BusinessLogicService:getAllProjects An error occured while getting the projects from DB");
			throw new BusinessLogicException("An exception occured while getting projects");
		}
	}
	/*
	 * Fetch All the products for one project
	 * */
	@Override
	public Collection<ProduitResponseDto> getAllProjectProduits(Long projectId) {
		List<ProduitResponseDto> produitsResponses = null;
		Project project;
		try {
			log.info("BusinessLogicService:getAllProjectProduits Execution started");
			project = projectRepo.findById(projectId).orElseThrow(()->new ProjectNotFoundException("Project not found"));
			List<Produit> produits = project.getProduits();
			if(produits.isEmpty()) {
				log.info("BusinessLogicService:getAllProjectProduits there is no produits in this project");
				produitsResponses = Collections.emptyList();
			}else {
				log.info("BusinessLogicService:getAllProjectProduits Convert each project entity into projectResponseDto");
				produitsResponses = produits.stream()
											.map(ValueMapper::toProduitResponseDto)
											.collect(Collectors.toList());
			}
			log.info("BusinessLogicService:getAllProjectProduits Execution finished successfully");
			return produitsResponses;
		}catch(Exception e) {
			log.error("BusinessLogicService:getAllProjectProduits An error occured while getting the produits from DB");
			throw new BusinessLogicException("An exception occured while getting produits");
		}
	}
	/*
	 * Get a project by id
	 * */
	@Override
	public ProjectResponseDto getProject(Long id) {
		try {
			log.info("BusinessLogicService:getProject Execution started");
			Project project = projectRepo.findById(id).orElseThrow(()->new ProjectNotFoundException("An Exception has occured while fetching the project from the DB"));
			ProjectResponseDto response = ValueMapper.toProjectResponseDto(project);
			log.info("BusinessLogicService:getProject Execution finished successfully");
			return response;
		}catch(Exception e) {
			log.error("BusinessLogicService:getProject Exception message {}",e.getMessage());
			throw new BusinessLogicException("An exception occured while getting the project from the DB");
		}
	}
	/*
	 * get a product by id
	 * */
	@Override
	public ProduitResponseDto getProduit(Long id) {
		try {
			log.info("BusinessLogicService:getProduit Execution started");
			Produit produit = produitRepo.findById(id).orElseThrow(()->new ProduitNotFoundException("An Exception has occured while fetching the project from the DB"));
			ProduitResponseDto response = ValueMapper.toProduitResponseDto(produit);
			log.info("BusinessLogicService:getProduit Execution finished successfully");
			return response;
		}catch(Exception e) {
			log.error("BusinessLogicService:getProduit Exception message {}",e.getMessage());
			throw new BusinessLogicException("An exception occured while getting the produit from the DB");
		}
	}
	
}
