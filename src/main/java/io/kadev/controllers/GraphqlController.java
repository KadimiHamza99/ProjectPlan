package io.kadev.controllers;

import io.kadev.models.graphql.CreateProjectWithProductsInput;
import io.kadev.services.BusinessLogicInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphqlController {
    @Autowired
    private BusinessLogicInterface service;

    @MutationMapping
    public boolean createProjectWithProducts(@Argument CreateProjectWithProductsInput input) {
        return true;
    }
}
