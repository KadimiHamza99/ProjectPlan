import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { MatDialogModule,MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTableModule} from '@angular/material/table';
import { ProjetModalComponent } from '../project-modal/project-modal.component';
import { GraphQLService } from '../graphql/graphqlService';

import { ActivatedRoute, Router} from '@angular/router';

export interface Project {
  id: number;
  nom: string;
  chargesFixesCommunes: number;
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
  constructor(private dialog: MatDialog,private route: ActivatedRoute,private graphQLService: GraphQLService, private router: Router) {}

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
  redirectToProjectDetails(project: Project): void {
    this.router.navigate(['/project', project.id]);
  }
}