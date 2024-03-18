Feature: tests fonctionnels

  Scenario: Création d'un projet avec des produits - Projet 1
    Given utilisateur cree son projet nomme "Projet 1" et avec un total des charges fixes communes de 100
    And maintenant il ajoute a son projet cette liste de produits :
      | name    | quantite | prixVenteUnitaire | coutVariableUnitaire | coutsFixesDirects | project_id | objectifGeneral | objectifParJour |
      | projet1 | 5        | 5                 | 5                    | 5                 | 5          | 5               | 5               |
      | projet2 | 58       | 55                | 50                   | 57                | 5          | 58              | 54              |
    When quand on va executer notre methode de comptabilite analytique
    Then notre projet doit avoir un resultat d'exploitation egale a 500
