import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './landing-page/components/home/home.component';
import {ProductCardComponent} from './product-card/product-card.component';
import {ProjectListComponent} from './project-list/project-list.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { NotFoundComponent } from './core/components/not-found/not-found.component';
const routes: Routes = [
  {path:'',component:HomeComponent},
  {path: 'projects', component:ProjectListComponent},
  { path: 'product/:id', component: ProductDetailsComponent },
  {path:'product',component:ProductCardComponent},
  {path: '**', component: NotFoundComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],



exports: [RouterModule]
})
export class AppRoutingModule { }
