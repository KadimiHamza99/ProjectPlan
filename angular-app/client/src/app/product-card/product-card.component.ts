import { Component, OnInit } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';

import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { GraphQLService } from '../graphql/graphqlService';

export interface Product {
  id: string;
  name: string;
  quantite: number;
  prixVenteUnitaire: number;
  coutVariableUnitaire: number;
  nombreVenteEstimeParSemaine: number;
  coutsFixesDirects: number;
  rentable: boolean;
  chiffreAffaire: number;
  margeCoutsVariables: number;
  margeCoutsDirects: number;
  partChiffreAffaire: number;
  repartitionProrata: number;
  margeCoutsComplets: number;
  seuilRentabilite: number;
  nombreVentesNecessaires: number;
  pointMort: number;
  objectifGeneral: number;
  objectifParJour: number;
  prixVenteOptimal: number;
}

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss'],
  standalone: true,
  imports: [MatCardModule, MatButtonModule,CommonModule]
})
export class ProductCardComponent implements OnInit {

  products : Product[] = [];
  projectId!: string;
  constructor(private route: ActivatedRoute,private graphQLService: GraphQLService, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id !== null) {
      const projectId = parseInt(id, 10);
      this.graphQLService.getProducts(projectId).subscribe((result : any ) => {
        const data = result.data;
        if (data) {
          this.products = data.getProductsForProject;
        }
      });
    }
  }
  
  redirectToProductDetails(product: Product): void {
  this.router.navigate(['/product', product.id]);
  }
}
