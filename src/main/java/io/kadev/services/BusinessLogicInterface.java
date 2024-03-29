package io.kadev.services;

import java.util.Collection;

import io.kadev.dto.ProductRequestDto;
import io.kadev.dto.ProductResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;

public interface BusinessLogicInterface {
	ProjectResponseDto createNewProject(ProjectRequestDto projectRequestDto);
	ProductResponseDto createNewProduit(ProductRequestDto productRequestDto);
	ProjectResponseDto calculMetrics(Long projectId);
	ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long projectId);
	ProductResponseDto updateProduit(ProductRequestDto productRequestDto, Long produitId);
	boolean deleteProject(Long projectId);
	boolean deleteProduit(Long produitId);
	boolean deleteProduit(Long projectId, Long produitId);
	Collection<ProjectResponseDto> getAllProjects();
	Collection<ProductResponseDto> getAllProjectProduits(Long projectId);
	ProjectResponseDto getProject(Long id);
	ProductResponseDto getProduit(Long id);
}
