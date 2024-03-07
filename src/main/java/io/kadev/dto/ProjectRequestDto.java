package io.kadev.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode
public class ProjectRequestDto {
	@NotBlank(message = "Un projet doit avoir un nom !")
	private String nom;
	@Min(value = 0,message="Les couts fixes ne peuvent pas etre negatifs !")
	@NotNull
	private double chargeFixesCommunes;
}
