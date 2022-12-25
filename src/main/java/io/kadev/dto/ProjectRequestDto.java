package io.kadev.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	private double coutsFixesCommunes;
}
