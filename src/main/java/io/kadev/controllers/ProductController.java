package io.kadev.controllers;


import io.kadev.dto.ProductResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

import io.kadev.dto.ProductRequestDto;
import io.kadev.services.BusinessLogicInterface;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
	
	@Autowired
	private BusinessLogicInterface service;
	
	/*
	 * Getting a product by passing an id as @PathVariable
	 * */
	@GetMapping("/get/{id}")
	public ResponseEntity<ProductResponseDto> getProduct(@PathVariable int id){
		ProductResponseDto response = service.getProduit((long) id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/*
	 * Create new product for a project
	 * */
	@PostMapping("/create")
	public ResponseEntity<ProductResponseDto> createNewProduct(@RequestBody @Valid ProductRequestDto product){
		ProductResponseDto response = service.createNewProduit(product);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	/*
	 * Update a product
	 * */
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable int id,
															@RequestBody @Valid ProductRequestDto product){
		ProductResponseDto response = service.updateProduit(product, (long) id);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	/*
	 * Delete a product from a project
	 * */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable int id){
		Boolean flag = service.deleteProduit((long) id);
		return new ResponseEntity<>(flag,HttpStatus.OK);
	}
	
}
