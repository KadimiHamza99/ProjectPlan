package io.kadev.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode
public class ProductRequestDto {
	@NotBlank(message = "Un produit doit avoir un nom!")
	private String name;
	@Min(value = 0, message = "La quantité doit etre sup à 0!")
	private int quantite;
	@Min(value = 0, message = "Le prix de vente unitaire doit etre sup à 0!")
	private double prixVenteUnitaire;
	@Min(value = 0, message = "Le cout variable unitaire doit etre sup à 0!")
	private double CoutVariableUnitaire;
	@Min(value = 0, message = "Les couts fixes directs doivent etre sup à 0!")
	private double coutsFixesDirects;
	@NotBlank(message = "Must contain a project ID!")
	private Long project_id;
	@NotBlank(message = "Must contain objectif general")
	private int objectifGeneral;
	@NotBlank(message = "Must contain objectif par jour")
	private int objectifParJour;
}
