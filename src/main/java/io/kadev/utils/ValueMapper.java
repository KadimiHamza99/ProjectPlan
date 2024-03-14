package io.kadev.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.kadev.dto.ProductResponseDto;
import io.kadev.models.Product;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.kadev.dto.ProductRequestDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.models.Project;
import io.kadev.repositories.ProjectRepository;

@Mapper(componentModel = "spring")
public interface ValueMapper {

	@Mapping(target = "chargesFixesCommunes", source = "chargeFixesCommunes")
	Project toProjectEntity(ProjectRequestDto projectRequestDto);

	ProjectResponseDto toProjectResponseDto(Project project);

	default Product toProduitEntity(ProductRequestDto productRequestDto,@Context Project project) {
		Product product = new Product();
		product.setName(productRequestDto.getName());
		product.setQuantite(productRequestDto.getQuantite());
		product.setPrixVenteUnitaire(productRequestDto.getPrixVenteUnitaire());
		product.setCoutVariableUnitaire(productRequestDto.getCoutVariableUnitaire());
		product.setCoutsFixesDirects(productRequestDto.getCoutsFixesDirects());
		product.setObjectifGeneral(productRequestDto.getObjectifGeneral());
		product.setObjectifParJour(productRequestDto.getObjectifParJour());
		// Il faut developper une methode dans un service qui contient getProjectById
		product.setProject(project);
		return product;
	}

	ProductResponseDto toProduitResponseDto(Product product);

}
