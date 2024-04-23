import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import gql from 'graphql-tag';
import { InMemoryCache } from '@apollo/client/core';
import { Product, Project } from '../project-modal/project-modal.component';

@Injectable({
  providedIn: 'root'
})
export class GraphQLService {
  constructor(private apollo: Apollo) {
    this.apollo.create({
        uri: 'http://localhost:8000/graphql',
        cache: new InMemoryCache()
    });
  }

  getProjects() {
    return this.apollo.query({
      query: gql`
        query GetProjects {
          getProjects {
            id
            nom
            coutsFixesCommunes
            resultatsExploitation
            quantiteTotal
          }
        }
      `
    });
  }

  createProjectWithProducts(project: Project) {
    console.log("projeect")
    console.log(project)
    return this.apollo.mutate({
      mutation: gql`
        mutation CreateProjectWithProducts($input: CreateProjectWithProductsInput!) {
          createProjectWithProducts(input: $input) {
            id
            nom
            coutsFixesCommunes
            resultatsExploitation
            quantiteTotal
          }
        }
      `,
      variables: {
        input: {
          nom: project.nom,
          chargeFixesCommunes: project.chargesFixesCommunes,
          products: project.produits.map((product: Product) => ({
            name: product.name,
            quantite: product.quantite,
            prixVenteUnitaire: product.prixVenteUnitaire,
            coutVariableUnitaire: product.coutVariableUnitaire,
            coutsFixesDirects: product.coutsFixesDirects,
            objectifGeneral: product.objectifGeneral,
            objectifParJour: product.objectifParJour
          }))
        }
      }
    });
  }

  getProducts(projectId: number) {
    return this.apollo.query({
      query: gql`
        query GetProductsForProject($id: Int!) {
          getProductsForProject(id: $id) {
            id
            name
            quantite
            prixVenteUnitaire
            CoutVariableUnitaire
            nombreVenteEstimeParSemaine
            coutsFixesDirects
            rentable
            chiffreAffaire
            margeCoutsVariables
            margeCoutsDirects
            partChiffreAffaire
            repartitionProrata
            margeCoutsComplets
            seuilRentabilite
            nombreVentesNecessaires
            pointMort
            objectifGeneral
            objectifParJour
            prixVenteOptimal
          }
        }
      `,
      variables: {
        id: projectId
      }
    });
  }

  
}
