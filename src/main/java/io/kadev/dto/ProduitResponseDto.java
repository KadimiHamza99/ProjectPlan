package io.kadev.dto;

import io.kadev.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProduitResponseDto {
	private Long id;
	private String name;
	private int quantite;
	private double prixVenteUnitaire;
	private double CoutVariableUnitaire;
	private int nombreVenteEstimeParSemaine;
	private double coutsFixesDirects;
	private boolean rentable;
	private Project project;
	private double chiffreAffaire;
	private double margeCoutsVariables;
	private double margeCoutsDirects;
	private double partChiffreAffaire;
	private double repartitionProrata;
	private double margeCoutsComplets;
	private double seuilRentabilite;
	private double nombreVentesNecessaires;
	private double pointMort;
}
