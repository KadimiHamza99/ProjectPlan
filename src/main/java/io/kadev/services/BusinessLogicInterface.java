package io.kadev.services;

import java.util.Collection;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProduitResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;

public interface BusinessLogicInterface {
	ProjectResponseDto createNewProject(ProjectRequestDto projectRequestDto);
	ProduitResponseDto createNewProduit(ProduitRequestDto produitRequestDto);
	ProjectResponseDto calculMetrics(Long projectId);
	ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long projectId);
	ProduitResponseDto updateProduit(ProduitRequestDto produitRequestDto, Long produitId);
	boolean deleteProject(Long projectId);
	boolean deleteProduit(Long produitId);
	Collection<ProjectResponseDto> getAllProjects();
	Collection<ProduitResponseDto> getAllProjectProduits(Long projectId);
	ProjectResponseDto getProject(Long id);
	ProduitResponseDto getProduit(Long id);
}
