package io.kadev.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProduitResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.exceptions.BusinessLogicException;
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
public class BusinessLogicService {
	
	private ProduitRepository produitRepo;
	private ProjectRepository projectRepo;
	
	public ProjectResponseDto createNewProject(ProjectRequestDto projectRequestDto) {
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
	
	public ProduitResponseDto createNewProduit(ProduitRequestDto produitRequestDto) {
		ProduitResponseDto produitResponseDto;
		try {	
			log.info("BusinessLogicService:createNewProduit execution started");
			Project project = projectRepo.findById(produitRequestDto.getProject_id()).get();
			Produit produit = ValueMapper.toProduitEntity(produitRequestDto,project);
			project.getProduits().add(produit);
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
	
	public ProjectResponseDto calculMetrics(Long projectId) {
		ProjectResponseDto projectResponseDto;
		try {
			log.info("BusinessLogicService:calculMetrics execution started");
			Project project = projectRepo.findById(projectId).orElseThrow(()->new ProjectNotFoundException("Exception occured while fetching the project from the DB !"));
			project.getProduits().stream().forEach(produit->{
				produit.setPartChiffreAffaire(produit.getChiffreAffaire()/produit.getProject().getChiffreAffaireTotal());
				produit.setRepartitionProrata(produit.getPartChiffreAffaire()*produit.getProject().getCoutsFixesCommunes());
				produit.setMargeCoutsComplets(produit.getMargeCoutsDirects()-produit.getRepartitionProrata());
				produit.setSeuilRentabilite((produit.getCoutsFixesDirects()+produit.getRepartitionProrata())/(produit.getMargeCoutsVariables()/produit.getChiffreAffaire()));
				/*
				 * Le nombre de vente necessaire pour que notre projet soit rentable
				 * */
				produit.setNombreVentesNecessaires((int) (produit.getSeuilRentabilite()/produit.getPrixVenteUnitaire()));
				/*
				 * Le nombre de semaines pour que notre projet soit rentable
				 * */
				produit.setPointMort(produit.getSeuilRentabilite()/(produit.getNombreVenteEstimeParSemaine()*produit.getPrixVenteUnitaire()));
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
	
}
