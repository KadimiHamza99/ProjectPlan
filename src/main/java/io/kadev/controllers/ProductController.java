package io.kadev.controllers;

import javax.validation.Valid;

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

import io.kadev.dto.ProduitRequestDto;
import io.kadev.dto.ProduitResponseDto;
import io.kadev.services.BusinessLogicInterface;
import io.kadev.utils.ValueMapper;
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
	public ResponseEntity<ProduitResponseDto> getProduct(@PathVariable int id){
		log.info("ProductController::getProduct getting the product with id = {}", id);
		ProduitResponseDto response = service.getProduit((long) id);
		log.info("ProductController::getProduct response => {}", ValueMapper.jsonAsString(response));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	/*
	 * Create new product for a project
	 * */
	@PostMapping("/create")
	public ResponseEntity<ProduitResponseDto> createNewProduct(@RequestBody @Valid ProduitRequestDto product){
		log.info("ProductController::createNewProduct request body {}", ValueMapper.jsonAsString(product));
		ProduitResponseDto response = service.createNewProduit(product);
		log.info("ProductController::createNewProduct response {}", ValueMapper.jsonAsString(response));
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	/*
	 * Update a product
	 * */
	@PutMapping("/update/{id}")
	public ResponseEntity<ProduitResponseDto> updateProduct(@PathVariable int id,
															@RequestBody @Valid ProduitRequestDto product){
		log.info("ProductController::updateProduct request body {}", ValueMapper.jsonAsString(product));
		ProduitResponseDto response = service.updateProduit(product, (long) id);
		log.info("ProductController::updateProduct response body {}", ValueMapper.jsonAsString(product));
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	/*
	 * Delete a product from a project
	 * */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable int id){
		log.info("ProductController::deleteProduct delete the product with the id = {}", id);
		Boolean flag = service.deleteProduit((long) id);
		log.info("ProductController::deleteProduct is the product deleted = {}", flag);
		return new ResponseEntity<>(flag,HttpStatus.OK);
	}
	
}
