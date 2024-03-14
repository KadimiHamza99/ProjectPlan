package io.kadev.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PRODUIT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private int quantite;
	private double prixVenteUnitaire;
	private double CoutVariableUnitaire;
	private double coutsFixesDirects;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	private Project project;

	/*
	 * Des attributs qui serts au Business Logic
	 */
	private double chiffreAffaire;
	private double margeCoutsVariables;
	private double margeCoutsDirects;
	private double partChiffreAffaire;
	private double repartitionCFCProrataCA;
	private double margeCoutsComplets;
	private double seuilRentabilite;
	private double nombreVentesNecessaires;
	private int objectifGeneral;
	private int objectifParJour;
	private double pointMort;
	private double prixVenteOptimal;
	
}
