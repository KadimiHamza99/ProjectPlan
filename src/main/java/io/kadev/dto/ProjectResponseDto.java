package io.kadev.dto;

import java.util.Arrays;
import java.util.List;

import io.kadev.models.Produit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectResponseDto {
	private Long id;
	private String nom;
	private double coutsFixesCommunes;
	private double resultatsExploitation;
	private int quantiteTotal;
	private List<Produit> produits = Arrays.asList();
}
