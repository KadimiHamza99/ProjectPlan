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
		/*ProjectRequestDto p1 = new ProjectRequestDto("Chocolatier",5000);
		service.createNewProject(p1);
		
		ProductRequestDto prod1 = new ProductRequestDto("T1",25000,12,3,120000,1L,20000,1);
		ProductRequestDto prod2 = new ProductRequestDto("T2",42000,9,2,160000,1L,40000,1);
		ProductRequestDto prod3 = new ProductRequestDto("T3",12000,16,6,55000,1L,10000,70);
		service.createNewProduit(prod1);
		service.createNewProduit(prod2);
		service.createNewProduit(prod3);

		service.calculMetrics(1L);*/
	}

}