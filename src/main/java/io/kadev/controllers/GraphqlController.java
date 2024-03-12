package io.kadev.controllers;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProduitResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.models.graphql.CreateProjectWithProductsInput;
import io.kadev.models.graphql.ProductInput;
import io.kadev.services.BusinessLogicInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
@Slf4j
public class GraphqlController {
    @Autowired
    private BusinessLogicInterface service;

    @MutationMapping
    public boolean createProjectWithProducts(@Argument CreateProjectWithProductsInput input) {
        try{
            ProjectRequestDto projectRequestDto = new ProjectRequestDto(input.getNom(), input.getChargeFixesCommunes());
            ProjectResponseDto projectOutput = service.createNewProject(projectRequestDto);
            for (ProductInput p : input.getProducts()) {
                ProduitRequestDto produit = new ProduitRequestDto(p.getName(), p.getQuantite(), p.getPrixVenteUnitaire(), p.getCoutVariableUnitaire(), p.getCoutsFixesDirects(), projectOutput.getId(), p.getObjectifGeneral(), p.getObjectifParJour());
                service.createNewProduit(produit);
            }
            service.calculMetrics(projectOutput.getId());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @QueryMapping
    public Collection<ProjectResponseDto> getProjects(){
        return  service.getAllProjects();
    }

    @QueryMapping
    public Collection<ProduitResponseDto> getProductsForProject(@Argument Long id){
        return service.getAllProjectProduits(id);
    }

    @MutationMapping
    public boolean deleteProject(@Argument Long id){
        try{
            service.deleteProject(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long projectId, @Argument Long productId){
        try{
            return service.deleteProduit(projectId, productId);
        }catch (Exception e){
            return false;
        }
    }

}
