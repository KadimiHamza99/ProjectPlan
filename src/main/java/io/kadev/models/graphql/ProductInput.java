package io.kadev.models.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String name;
    private int quantite;
    private float prixVenteUnitaire;
    private float coutVariableUnitaire;
    private float coutsFixesDirects;
    private int objectifGeneral;
    private int objectifParJour;
}
