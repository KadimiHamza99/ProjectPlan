package io.kadev.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
	
	//Constructeur
	public Produit(String n,int qte, double pvu, double cvu, int nbve, double cfd, Project p) {
		this.project = p;
		this.name = n;
		this.quantite = qte;
		this.prixVenteUnitaire = pvu;
		this.CoutVariableUnitaire = cvu;
		this.nombreVenteEstimeParSemaine = nbve;
		this.coutsFixesDirects = cfd;
		/*
		 * Calculer CA,MCV,MCD
		 * */
		this.chiffreAffaire = this.quantite*this.prixVenteUnitaire;
		this.margeCoutsVariables = this.chiffreAffaire - this.CoutVariableUnitaire*this.quantite;
		this.margeCoutsDirects = this.margeCoutsVariables - this.coutsFixesDirects;	
		/*
		 * Ajouter le produit dans la liste des produit du projet
		 * */
		this.project.getProduits().add(this);
		this.project.setChiffreAffaireTotal(this.project.getChiffreAffaireTotal()+this.chiffreAffaire);



	}
	
}
