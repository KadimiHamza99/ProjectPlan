package io.kadev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.services.BusinessLogicService;

@SpringBootApplication
public class ProjectPlanApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProjectPlanApplication.class, args);
	}
	
	/*
	 * Cette partie ce n'est que pour les tests ! 
	 * */

	@Autowired
	private BusinessLogicService service;
	@Override
	public void run(String... args) throws Exception {
		ProjectRequestDto p1 = new ProjectRequestDto("Patisserie",3500.0);
//		ProduitRequestDto prod2 = new ProduitRequestDto("Pain",26000,16.0,3.3,281,110.0,2L);
		service.createNewProject(p1);
		
		ProduitRequestDto prod1 = new ProduitRequestDto("Pain",25000,12.0,3.0,211,120.0,1L);
		service.createNewProduit(prod1);
	}

	/*
	 * La partie des tests est finie
	 * */

}