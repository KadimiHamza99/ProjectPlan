package io.kadev.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectRequestDto {
	@NotBlank(message = "Un projet doit avoir un nom !")
	@UniqueElements(message = "Un nom de projet doit etre unique !")
	private String nom;
	@Min(value = 0)
	@NotNull
	private double coutsFixesCommunes;
}
