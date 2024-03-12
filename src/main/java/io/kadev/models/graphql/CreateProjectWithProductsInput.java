package io.kadev.models.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectWithProductsInput {
    private String nom;
    private float chargeFixesCommunes;
    private ArrayList<ProductInput> products;
}
