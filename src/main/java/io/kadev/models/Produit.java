package io.kadev.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private int quantite;
	private double prixVenteUnitaire;
	private double CoutVariableUnitaire;
	private int nombreVenteEstimeParSemaine;
	private double coutsFixesDirects;
	private boolean rentable;
	
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
	private double repartitionProrata;
	private double margeCoutsComplets;
	private double seuilRentabilite;
	private int nombreVentesNecessaires;
	private double pointMort;
	
}
