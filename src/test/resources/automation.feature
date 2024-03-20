Feature: tests fonctionnels

  Scenario: Création d'un projet avec des produits - Projet 1
    Given utilisateur cree son projet nomme Chocolatier et avec un total des charges fixes communes de 300000
    And maintenant il ajoute a son projet cette liste de produits :
      | name             | quantite | prixVenteUnitaire | coutVariableUnitaire | coutsFixesDirects | project_id | objectifGeneral | objectifParJour |
      | Oeufs            | 25000    | 12                | 3                    | 120000            | 1          | 14000           | 72               |
      | Lot 3 Plaquettes | 42000    | 9                 | 2                    | 160000            | 1          | 15000           | 67              |
      | Nougat           | 12000    | 16                | 6                    | 55000             | 1          | 12000           | 50              |
    When quand on va executer notre methode de comptabilite analytique
    Then notre projet doit avoir un resultat d'exploitation egale a 4000
    And l analyse sur nos produits :
      | name             | seuilRentabilite   | nombreVentesNecessaires | prixVenteOptimal   |
      | Oeufs            | 297931.03448275867 | 24827.586206896554      | 18.51724137931035  |
      | Lot 3 Plaquettes | 373300.49261083745 | 41477.832512315275      | 23.667524446731857 |
      | Nougat           | 193931.0344827586  | 12120.689655172413      | 18.120689655172413 |
