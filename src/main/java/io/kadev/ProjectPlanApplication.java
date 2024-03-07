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
		ProjectRequestDto p1 = new ProjectRequestDto("Chocolatier",300000);
		service.createNewProject(p1);
		
		ProduitRequestDto prod1 = new ProduitRequestDto("T1",5000,70,30,80000,1L,12000,50);
		ProduitRequestDto prod2 = new ProduitRequestDto("T2",2000,120,40,100000,1L,12000,50);
		ProduitRequestDto prod3 = new ProduitRequestDto("T3",800,800,420,210000,1L,12000,50);
		service.createNewProduit(prod1);
		service.createNewProduit(prod2);
		service.createNewProduit(prod3);

		service.calculMetrics(1L);
	}

	/*
	 * La partie des tests est finie
	 * */

}