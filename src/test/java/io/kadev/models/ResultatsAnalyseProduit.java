package io.kadev.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultatsAnalyseProduit {
    private String name;
    private float seuilRentabilite;
    private int nombreVentesNecessaires;
    private float prixVenteOptimal;
}
