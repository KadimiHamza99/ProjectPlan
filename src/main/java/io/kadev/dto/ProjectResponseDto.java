package io.kadev.dto;

import java.util.Arrays;
import java.util.List;

import io.kadev.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectResponseDto {
	private Long id;
	private String nom;
	private double chargesFixesCommunes;
	private double resultatsExploitation;
	private int quantiteTotal;
	private double chiffreAffaireTotal;
	private List<Product> products = Arrays.asList();
}
