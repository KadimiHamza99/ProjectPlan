import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { MatDialogModule,MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTableModule} from '@angular/material/table';
import { ProjetModalComponent } from '../project-modal/project-modal.component';
import { GraphQLService } from '../graphql/graphqlService';

export interface Project {
  nom: string;
  coutsFixesCommunes: number;
  chiffreAffaireTotal: number;
  resultatsExploitation: number;
  quantiteTotal: number;
}

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss'],
  standalone: true,
  changeDetection: ChangeDetectionStrategy.Default,
  imports : [MatTableModule,MatDialogModule]
})
export class ProjectListComponent implements OnInit{
  projects: any[] = [];
  
  ajouterProjet() {
    // Ajoutez ici la logique pour ajouter un nouveau projet à la liste
    const nouveauProjet: Project = {
      nom: 'Nouveau Projet',
      coutsFixesCommunes: 0,
      chiffreAffaireTotal: 0,
      resultatsExploitation: 0,
      quantiteTotal: 0
    };
    this.projects.push(nouveauProjet);
    this.projects = [...this.projects];
  }
  constructor(private dialog: MatDialog,private graphQLService: GraphQLService) {}

  ngOnInit(): void {
    this.graphQLService.getProjects().subscribe((result : any ) => {
      const data = result.data;
      if (data) {
        this.projects = data.getProjects;
      }
    });
  }
  ouvrirModal(): void {
    const dialogRef = this.dialog.open(ProjetModalComponent, {
      width: '450px',
      height: '530px', 
      disableClose: true // Empêche la fermeture du modal en cliquant à l'extérieur ou en appuyant sur la touche Escape
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.graphQLService.createProjectWithProducts(result).subscribe((result : any  ) => {
          const data = result.data;
          if (data) {
            // Ajoutez le nouveau projet à la liste
            this.projects.push(data.createProjectWithProducts);
          }
        });
      }
    });
  }
}