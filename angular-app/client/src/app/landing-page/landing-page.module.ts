import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home/home.component';
import { MaterialDesignModule } from '../material-design/material-design.module';

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    MaterialDesignModule,
    CommonModule
  ],
  exports: [
    HomeComponent
  ]
})
export class LandingPageModule { }
