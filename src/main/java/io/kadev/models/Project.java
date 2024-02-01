package io.kadev.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import jakarta.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String nom;
	private double coutsFixesCommunes;
	private double chiffreAffaireTotal;
	private double resultatsExploitation;
	private int quantiteTotal;

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
