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
  /*projects: Project[] = [
    { nom: 'Projet 1', chargesFixesCommunes: 1000, chiffreAffaireTotal: 5000, resultatsExploitation: 4000, quantiteTotal: 10 },
    { nom: 'Projet 2', chargesFixesCommunes: 1500, chiffreAffaireTotal: 6000, resultatsExploitation: 4500, quantiteTotal: 15 },
    { nom: 'Projet 3', chargesFixesCommunes: 1200, chiffreAffaireTotal: 5500, resultatsExploitation: 4300, quantiteTotal: 12 },
    // Ajoutez d'autres projets si nécessaire
  ];*/
  // Méthode pour ajouter un nouveau projet
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
      console.log("Data")
      console.log(data)
      if (data) {
        this.projects = data.getProjects;
        console.log("projects")
        console.log(this.projects)
      }
    });
  }
  ouvrirModal(): void {
    const dialogRef = this.dialog.open(ProjetModalComponent, {
      width: '450px',
      height: '530px', // Définissez la largeur du modal selon vos préférences
      disableClose: true // Empêche la fermeture du modal en cliquant à l'extérieur ou en appuyant sur la touche Escape
    });

    dialogRef.afterClosed().subscribe(result => {
      // Une fois que le modal est fermé, vérifiez si un nouveau projet a été ajouté et mettez à jour la liste des projets si nécessaire
      if (result) {
        this.projects.push(result);
      }
    });
  }
}