package io.kadev.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "PROJECT")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private double coutsFixesCommunes;
	private double chiffreAffaireTotal;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Produit> produits = new ArrayList<Produit>(Arrays.asList());

	// Constructeur
	public Project(String n, double cfc) {
		this.nom = n;
		this.coutsFixesCommunes = cfc;
		this.chiffreAffaireTotal = 0;
		produits.stream().forEach(p -> this.chiffreAffaireTotal = p.getChiffreAffaire());
	}

}
