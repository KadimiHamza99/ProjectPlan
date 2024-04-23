package io.kadev;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import io.kadev.dto.ProductRequestDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.models.Product;
import io.kadev.models.ResultatsAnalyseProduit;
import io.kadev.services.BusinessLogicInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@CucumberContextConfiguration
@CucumberOptions(features = "src/test/resources")
@Slf4j
public class AutomationTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BusinessLogicInterface service;

    @Given("utilisateur cree son projet nomme {} et avec un total des charges fixes communes de {}")
    public void utilisateurCreeSonProjetNommeEtAvecUnTotalDesChargesFixesCommunesDe(String nom, int charges) {
        service.createNewProject(new ProjectRequestDto(nom,charges));
    }

    @And("maintenant il ajoute a son projet cette liste de produits :")
    public void maintenantIlAjouteASonProjetCetteListeDeProduits(List<ProductRequestDto> produits) {
        for (ProductRequestDto p : produits) {
            service.createNewProduit(p);
        }
    }

    @When("quand on va executer notre methode de comptabilite analytique pour le projet {}")
    public void quandOnVaExecuterNotreMethodeDeComptabiliteAnalytique(long projectId) {
        service.calculMetrics(projectId);
    }

    @Then("notre projet {} doit avoir un resultat d'exploitation egale a {}")
    public void notreProjetDoitAvoirUnResultatDExploitationEgaleA(long projectId, String resultatExploitation ) {
        ProjectResponseDto project = service.getProject(projectId);
        assertTrue(Math.abs(project.getResultatsExploitation()-Integer.valueOf(resultatExploitation))<1);
    }

    @And("l analyse sur les produits pour le projet {} :")
    public void lAnalyseSurNosProduits(long projectId, List<ResultatsAnalyseProduit> resultats) {
        ProjectResponseDto project = service.getProject(projectId);
        for(ResultatsAnalyseProduit r : resultats){
            Product product = project.getProducts().stream()
                    .filter(p -> p.getName().equals(r.getName()))
                    .findFirst().get();
            assertTrue(Math.abs(r.getPrixVenteOptimal()-product.getPrixVenteOptimal())<0.1);
            assertTrue(Math.abs(r.getNombreVentesNecessaires()-(int) Math.floor(product.getNombreVentesNecessaires()))<0.1);
            assertTrue(Math.abs(r.getSeuilRentabilite()-product.getSeuilRentabilite())<0.1);
        }
    }

    @DataTableType
    public ProductRequestDto defineProductRequestDto(Map<String, String> entry) {
        return objectMapper.convertValue(entry, ProductRequestDto.class);
    }

    @DataTableType
    public ResultatsAnalyseProduit defineResultatsAnalyseProduit(Map<String, String> entry) {
        return new ResultatsAnalyseProduit(
                entry.get("name")
                , Float.valueOf(entry.get("seuilRentabilite"))
                , Float.valueOf(entry.get("nombreVentesNecessaires")).intValue()
                , Float.valueOf(entry.get("prixVenteOptimal"))
        );
    }
}
