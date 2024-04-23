import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';

export interface Project {
  nom: string;
  chargesFixesCommunes: number;
  produits: Array<Product>;
}

export interface Product {
  name: string;
  quantite: number;
  prixVenteUnitaire: number;
  coutVariableUnitaire: number;
  coutsFixesDirects: number;
  objectifGeneral: number;
  objectifParJour: number;
}

@Component({
  selector: 'app-project-modal',
  templateUrl: './project-modal.component.html',
  styleUrls: ['./project-modal.component.scss'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, CommonModule, ReactiveFormsModule]
})
export class ProjetModalComponent {
  //@ViewChild('projectForm') projectForm!: NgForm;
  projectForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<ProjetModalComponent>,
    private fb: FormBuilder
  ) {
    this.projectForm = this.fb.group({
      nom: ['', Validators.required],
      chargesFixesCommunes: [0, Validators.required],
      produits: this.fb.array([])
    });
  }

  /*get produits(): FormArray {
    return this.projectForm.get('produits') as FormArray;
  }*/

  createProductForm(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      quantite: [0, Validators.required],
      prixVenteUnitaire: [0, Validators.required],
      coutVariableUnitaire: [0, Validators.required],
      coutsFixesDirects: [0, Validators.required],
      objectifGeneral: [0, Validators.required],
      objectifParJour: [0, Validators.required]
    });
  }

  get produits(): FormArray {
    return this.projectForm.get('produits') as FormArray;
  }

  ajouterProduit(): void {
    this.produits.push(this.createProductForm());
  }

  ajouterProjet(): void {
    if (this.projectForm.valid) {
      const nouveauProjet: Project = this.projectForm.value;
      this.dialogRef.close(nouveauProjet);
    }
  }

  fermerModal(): void {
    this.dialogRef.close();
  }
  /*ajouterProduit(): void {
    const produitForm = this.fb.group({
      name: ['', Validators.required],
      quantite: [0, Validators.required],
      prixVenteUnitaire: [0, Validators.required],
      coutVariableUnitaire: [0, Validators.required],
      coutsFixesDirects: [0, Validators.required],
      objectifGeneral: [0, Validators.required],
      objectifParJour: [0, Validators.required]
    });
    this.produits.push(produitForm);
  }

  ajouterProjet(): void {
    if (this.projectForm.valid) {
      const nouveauProjet: Project = this.projectForm.value;
      console.log(nouveauProjet);
      this.dialogRef.close(nouveauProjet);
    }
  }

  fermerModal(): void {
    this.dialogRef.close();
  }*/
}
