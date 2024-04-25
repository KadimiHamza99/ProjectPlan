import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';

import { GraphQLService } from '../graphql/graphqlService';

export interface ProductRes {
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
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
  standalone: true,
  imports: [MatCardModule, MatButtonModule]
})
export class ProductDetailsComponent implements OnInit {
  productId!: string;
  product : any;
  id! : number;
  constructor(private route: ActivatedRoute, private graphQLService: GraphQLService) { }

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id') || '';
    if (this.productId !== null) {
      this.id = parseInt(this.productId, 10);
      this.graphQLService.getProductById(this.id).subscribe((product: ProductRes) => {
        this.product = product;
      });
    }
  }

}
