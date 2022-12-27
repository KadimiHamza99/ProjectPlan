import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { RouterModule } from '@angular/router';
import { FeaturesRoutingModule } from './features-routing.module';

@NgModule({
  declarations: [
    ProjectListComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FeaturesRoutingModule
  ],
  exports: [
    ProjectListComponent
  ]
})
export class FeaturesModule { }
