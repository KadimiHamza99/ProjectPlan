package io.kadev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kadev.models.Produit;
import io.kadev.models.Project;
import io.kadev.repositories.ProduitRepository;
import io.kadev.repositories.ProjectRepository;

@SpringBootApplication
public class ProjectPlanApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProjectPlanApplication.class, args);
	}
	
	/*
	 * Cette partie ce n'est que pour les tests ! 
	 * */

	@Autowired
	private ProjectRepository projR;
	@Override
	public void run(String... args) throws Exception {
		Project p1 = new Project("Patisserie",3500);
		Produit prod1 = new Produit("Pain",25000,12,3,211,120000,p1);
		Produit prod2 = new Produit("Petit-pain",42000,9,2,123,160000,p1);
		projR.save(p1);
	}

	/*
	 * La partie des tests est finie
	 * */

}