package io.kadev.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode
public class ProduitRequestDto {
	@NotBlank(message = "Un produit doit avoir un nom!")
	private String name;
	@Min(value = 0, message = "La quantité doit etre sup à 0!")
	private int quantite;
	@Min(value = 0, message = "Le prix de vente unitaire doit etre sup à 0!")
	private double prixVenteUnitaire;
	@Min(value = 0, message = "Le cout variable unitaire doit etre sup à 0!")
	private double CoutVariableUnitaire;
	@Min(value = 0, message = "Le nombre de vente éstimé doit etre sup à 0!")
	private int nombreVenteEstimeParSemaine;
	@Min(value = 0, message = "Les couts fixes directs doivent etre sup à 0!")
	private double coutsFixesDirects;
	@NotBlank(message = "Must contain a project ID!")
	private Long project_id;
}
