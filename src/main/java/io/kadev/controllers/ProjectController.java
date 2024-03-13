package io.kadev.controllers;

import java.util.Collection;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.kadev.dto.ProduitResponseDto;
import io.kadev.dto.ProjectRequestDto;
import io.kadev.dto.ProjectResponseDto;
import io.kadev.services.BusinessLogicInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	private BusinessLogicInterface service;
	
	/*
	 * Getting a project by passing an id as @PathVariable
	 * */
	@GetMapping("/get/{id}")
	public ResponseEntity<ProjectResponseDto> getProject(@PathVariable int id){
		ProjectResponseDto response = service.getProject((long) id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/*
	 * Create new Project
	 * */
	@PostMapping("/create")
	public ResponseEntity<ProjectResponseDto> createNewProject(@RequestBody @Valid ProjectRequestDto project){
		ProjectResponseDto response = service.createNewProject(project);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	/*
	 * fetch all project products
	 * */
	@GetMapping("/get-products/{id}")
	public ResponseEntity<Collection<ProduitResponseDto>> getProjectProducts(@PathVariable int id){
		Collection<ProduitResponseDto> response =  service.getAllProjectProduits((long) id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/*
	 * Fetch all existing projects
	 * */
	@GetMapping("/get-all")
	public ResponseEntity<Collection<ProjectResponseDto>> getProjects(){
		Collection<ProjectResponseDto> response = service.getAllProjects();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/*
	 * Update project
	 * */
	@PutMapping("/update/{id}")
	public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable int id,
															@RequestBody @Valid ProjectRequestDto project){
		ProjectResponseDto response = service.updateProject(project, (long) id);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	/*
	 * Delete Project implies deleting all the existing products of the project
	 * */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteProject(@PathVariable int id){
		Boolean flag = service.deleteProject((long) id);
		return new ResponseEntity<>(flag,HttpStatus.OK);
	}
	/*
	 * Compute metrics
	 * */
	@GetMapping("/compute-metrics")
	public ResponseEntity<ProjectResponseDto> computeMetrics(@RequestParam int id){
		ProjectResponseDto response = service.calculMetrics((long) id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
