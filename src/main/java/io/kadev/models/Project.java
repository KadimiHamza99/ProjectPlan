package io.kadev.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROJECT")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String nom;
	private double chargesFixesCommunes;
	private double chiffreAffaireTotal;
	private double resultatsExploitation;
	private int quantiteTotal;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> products = new ArrayList<Product>(Arrays.asList());

	// Constructeur
	public Project(String n, double cfc) {
		this.nom = n;
		this.chargesFixesCommunes = cfc;
		this.chiffreAffaireTotal = 0;
		products.stream().forEach(p -> this.chiffreAffaireTotal = p.getChiffreAffaire());
	}

}
