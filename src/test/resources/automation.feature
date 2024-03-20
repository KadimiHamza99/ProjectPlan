Feature: tests fonctionnels

  Scenario: Création d'un projet avec des produits - Chocolatier
    Given utilisateur cree son projet nomme Chocolatier et avec un total des charges fixes communes de 300000
    And maintenant il ajoute a son projet cette liste de produits :
      | name             | quantite | prixVenteUnitaire | coutVariableUnitaire | coutsFixesDirects | project_id | objectifGeneral | objectifParJour |
      | Oeufs            | 25000    | 12                | 3                    | 120000            | 1          | 14000           | 72               |
      | Lot 3 Plaquettes | 42000    | 9                 | 2                    | 160000            | 1          | 15000           | 67              |
      | Nougat           | 12000    | 16                | 6                    | 55000             | 1          | 12000           | 50              |
    When quand on va executer notre methode de comptabilite analytique pour le projet 1
    Then notre projet 1 doit avoir un resultat d'exploitation egale a 4000
    And l analyse sur les produits pour le projet 1 :
      | name             | seuilRentabilite   | nombreVentesNecessaires | prixVenteOptimal   |
      | Oeufs            | 297931.03448275867 | 24827.586206896554      | 18.51724137931035  |
      | Lot 3 Plaquettes | 373300.49261083745 | 41477.832512315275      | 23.667524446731857 |
      | Nougat           | 193931.0344827586  | 12120.689655172413      | 18.120689655172413 |


  Scenario: Création d'un projet avec des produits - Trottinet
    Given utilisateur cree son projet nomme Trotinet et avec un total des charges fixes communes de 300000
    And maintenant il ajoute a son projet cette liste de produits :
      | name                  | quantite | prixVenteUnitaire | coutVariableUnitaire | coutsFixesDirects | project_id | objectifGeneral | objectifParJour |
      | Trotinette enfant     | 5000     | 70                | 30                   | 80000             | 2          | 4500            | 13              |
      | Trotinette adulte     | 2000     | 120               | 40                   | 100000            | 2          | 1900            | 15              |
      | Trotinette electrique | 800      | 800               | 420                  | 210000            | 2          | 800             | 7               |
    When quand on va executer notre methode de comptabilite analytique pour le projet 2
    Then notre projet 2 doit avoir un resultat d'exploitation egale a -26000
    And l analyse sur les produits pour le projet 2 :
      | name                  | seuilRentabilite  | nombreVentesNecessaires | prixVenteOptimal  |
      | Trotinette enfant     | 289390.243902439  | 4134.1463414634145      | 93.60225140712944 |
      | Trotinette adulte     | 237804.8780487805 | 1981.7073170731708      | 92.84552845528455 |
      | Trotinette electrique | 770731.7073170734 | 963.4146341463417       | 681.4982578397213 |
