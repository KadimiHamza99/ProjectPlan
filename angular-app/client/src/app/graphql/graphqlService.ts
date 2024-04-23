import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import gql from 'graphql-tag';
import { InMemoryCache } from '@apollo/client/core';

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
}
