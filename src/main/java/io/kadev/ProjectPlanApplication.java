package io.kadev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kadev.dto.ProductRequestDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.services.BusinessLogicInterface;

@SpringBootApplication
public class ProjectPlanApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProjectPlanApplication.class, args);
	}

	@Autowired
	private BusinessLogicInterface service;
	
	@Override
	public void run(String... args) throws Exception {
		/*ProjectRequestDto p1 = new ProjectRequestDto("TEST",4000);
		service.createNewProject(p1);
		
		ProductRequestDto prod1 = new ProductRequestDto
				("T1",500,2.5,1,600,1L,500,50);
		ProductRequestDto prod2 = new ProductRequestDto
				("T2",1000,1,0.5,1000,1L,1000,100);
		service.createNewProduit(prod1);
		service.createNewProduit(prod2);

		service.calculMetrics(1L);*/
	}

}