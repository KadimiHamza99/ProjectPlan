Feature: tests fonctionnels

  Scenario: Création d'un projet avec des produits - Projet 1
    Given utilisateur cree son projet nomme projet 1 et avec un total des charges fixes communes de 100
    And maintenant il ajoute a son projet cette liste de produits :
      | name     | quantite | prixVenteUnitaire | coutVariableUnitaire | coutsFixesDirects | project_id | objectifGeneral | objectifParJour |
      | produit1 | 5        | 5764              | 557                  | 538               | 1          | 5272            | 542             |
      | produit2 | 2        | 5557              | 505                  | 57                | 1          | 358             | 54              |
    When quand on va executer notre methode de comptabilite analytique
    Then notre projet doit avoir un resultat d'exploitation egale a 35444
    And l analyse sur nos produits :
      | name     | seuilRentabilite  | nombreVentesNecessaires | prixVenteOptimal  |
      | produit1 | 675.4397102443921 | 0.10351082800277688     | 5764.005628866043 |
      | produit2 | 93.31065481123822 | 0.002833105476476134    | 5557.007854714915 |
