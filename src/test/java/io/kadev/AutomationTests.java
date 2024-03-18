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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@CucumberContextConfiguration
@CucumberOptions(features = "src/test/resources")
@Slf4j
public class AutomationTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Given("utilisateur cree son projet nomme {} et avec un total des charges fixes communes de {}")
    public void utilisateurCreeSonProjetNommeEtAvecUnTotalDesChargesFixesCommunesDe(String nom, int charges) {
        log.warn("GIVEN");
        log.info(nom);
        log.info("{}",charges);
    }

    @And("maintenant il ajoute a son projet cette liste de produits :")
    public void maintenantIlAjouteASonProjetCetteListeDeProduits(List<ProductRequestDto> produits) {
        log.warn("AND");
        for (ProductRequestDto product:produits) {
            log.info(product.getName());
        }
    }

    @When("quand on va executer notre methode de comptabilite analytique")
    public void quandOnVaExecuterNotreMethodeDeComptabiliteAnalytique() {
        log.warn("WHEN");
    }

    @Then("notre projet doit avoir un resultat d'exploitation egale a {}")
    public void notreProjetDoitAvoirUnResultatDExploitationEgaleA(int resultatExploitation ) {
        log.warn("THEN");
        log.info("{}", resultatExploitation);
    }

    @DataTableType
    public ProductRequestDto defineProductRequestDto(Map<String, String> entry) {
        return objectMapper.convertValue(entry, ProductRequestDto.class);
    }

}
