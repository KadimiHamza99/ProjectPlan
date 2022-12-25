package io.kadev.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProduitResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.models.Produit;
import io.kadev.models.Project;
import io.kadev.repositories.ProjectRepository;

public class ValueMapper {
	/*
	 * Cette methode permet de convertir un objet projectRequestDto en un objet
	 * Project
	 */
	public static Project toProjectEntity(ProjectRequestDto projectRequestDto) {
		Project project = new Project();
		project.setNom(projectRequestDto.getNom());
		project.setCoutsFixesCommunes(projectRequestDto.getCoutsFixesCommunes());
		return project;
	}

	/*
	 * Cette methode permet de convertir un objet Project en un objet
	 * ProjectResponseDto
	 */
	public static ProjectResponseDto toProjectResponseDto(Project project) {
		ProjectResponseDto projectResponseDto = new ProjectResponseDto();
		projectResponseDto.setId(project.getId());
		projectResponseDto.setNom(project.getNom());
		projectResponseDto.setCoutsFixesCommunes(project.getCoutsFixesCommunes());
		projectResponseDto.setProduits(project.getProduits());
		projectResponseDto.setResultatsExploitation(project.getResultatsExploitation());
		projectResponseDto.setQuantiteTotal(project.getQuantiteTotal());
		return projectResponseDto;
	}

	/*
	 * Cette methode permet de convertir un objet produitRequestDto en un objet
	 * Produit
	 */
	@Autowired
	static ProjectRepository projectRepository;

	public static Produit toProduitEntity(ProduitRequestDto produitRequestDto,Project project) {
		Produit produit = new Produit();
		produit.setName(produitRequestDto.getName());
		produit.setQuantite(produitRequestDto.getQuantite());
		produit.setPrixVenteUnitaire(produitRequestDto.getPrixVenteUnitaire());
		produit.setCoutVariableUnitaire(produitRequestDto.getCoutVariableUnitaire());
		produit.setNombreVenteEstimeParSemaine(produitRequestDto.getNombreVenteEstimeParSemaine());
		produit.setCoutsFixesDirects(produitRequestDto.getCoutsFixesDirects());
		// Il faut developper une methode dans un service qui contient getProjectById
		produit.setProject(project);
		return produit;
	}

	/*
	 * Cette methode permet de convertir un objet Produit en un objet
	 * ProduitResponseDto
	 */
	public static ProduitResponseDto toProduitResponseDto(Produit produit) {
		ProduitResponseDto produitResponseDto = new ProduitResponseDto();
		produitResponseDto.setChiffreAffaire(produit.getChiffreAffaire());
		produitResponseDto.setCoutsFixesDirects(produit.getCoutsFixesDirects());
		produitResponseDto.setCoutVariableUnitaire(produit.getCoutVariableUnitaire());
		produitResponseDto.setId(produit.getId());
		produitResponseDto.setMargeCoutsComplets(produit.getMargeCoutsComplets());
		produitResponseDto.setMargeCoutsDirects(produit.getMargeCoutsDirects());
		produitResponseDto.setMargeCoutsVariables(produit.getMargeCoutsVariables());
		produitResponseDto.setName(produit.getName());
		produitResponseDto.setNombreVenteEstimeParSemaine(produit.getNombreVenteEstimeParSemaine());
		produitResponseDto.setNombreVentesNecessaires(produit.getNombreVentesNecessaires());
		produitResponseDto.setPartChiffreAffaire(produit.getPartChiffreAffaire());
		produitResponseDto.setPointMort(produit.getPointMort());
		produitResponseDto.setPrixVenteUnitaire(produit.getPrixVenteUnitaire());
		produitResponseDto.setProject(produit.getProject());
		produitResponseDto.setQuantite(produit.getQuantite());
		produitResponseDto.setRentable(produit.isRentable());
		produitResponseDto.setRepartitionProrata(produit.getRepartitionProrata());
		produitResponseDto.setSeuilRentabilite(produit.getSeuilRentabilite());
		return produitResponseDto;
	}

	public static String jsonAsString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
