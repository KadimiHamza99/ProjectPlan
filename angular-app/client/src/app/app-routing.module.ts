import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './landing-page/components/home/home.component';
import { NotFoundComponent } from './core/components/not-found/not-found.component';
const routes: Routes = [
  {path: 'projects', loadChildren: () =>import('./features/features.module').then(m => m.FeaturesModule)},
  {path:'',component:HomeComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],



exports: [RouterModule]
})
export class AppRoutingModule { }
