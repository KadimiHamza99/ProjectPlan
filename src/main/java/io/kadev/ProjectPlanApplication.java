package io.kadev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.services.BusinessLogicInterface;

@SpringBootApplication
public class ProjectPlanApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProjectPlanApplication.class, args);
	}
	
	/*
	 * Cette partie ce n'est que pour les tests ! 
	 * */

	@Autowired
	private BusinessLogicInterface service;
	
	@Override
	public void run(String... args) throws Exception {
		ProjectRequestDto p1 = new ProjectRequestDto("Chocolatier",500);
		service.createNewProject(p1);
		
//		ProduitRequestDto prod1 = new ProduitRequestDto("Oeufs",25000,12.0,3.0,100,120000.0,1L);
//		ProduitRequestDto prod2 = new ProduitRequestDto("Lot3",42000,9.0,2.0,200,160000.0,1L);
		ProduitRequestDto prod3 = new ProduitRequestDto("Nougat",300,16.0,10.0,15,0,1L);
		ProduitRequestDto prod4 = new ProduitRequestDto("Telephone",20,800,705,3,0,1L);
//		service.createNewProduit(prod1);
//		service.createNewProduit(prod2);
		service.createNewProduit(prod3);
		service.createNewProduit(prod4);
//		service.deleteProject(1L);
//		service.updateProduit(new ProduitRequestDto("Oeufs",35000,32.0,3.0,10,12000.0,1L), 2L);

		service.calculMetrics(1L);
	}

	/*
	 * La partie des tests est finie
	 * */

}