package com.store.controller;

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

import com.store.Dtos.ApiResponseMessage;
import com.store.Dtos.CategoryDto;
import com.store.Dtos.PageableResponse;
import com.store.Dtos.ProductDto;
import com.store.services.CategoryService;
import com.store.services.ProductService;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
	
	@Autowired
	public CategoryService categoryservice;
	
	
	@Autowired
	public ProductService productService;
	
	@PostMapping()
	public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto){
		CategoryDto create = categoryservice.create(categoryDto);
		return new ResponseEntity<CategoryDto>(create, HttpStatus.CREATED);	
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> update(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
		
		CategoryDto update = categoryservice.update(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(update, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable String categoryId){
		categoryservice.delete(categoryId);
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is deleted Successfully!").status(HttpStatus.OK).success(true).build();
		 return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<PageableResponse<CategoryDto>>  getAll(
			@RequestParam(value="pageNumber", defaultValue ="0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue ="10", required=false) int pageSize,
			@RequestParam(value="sortBy", defaultValue="title", required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir
			){
		PageableResponse<CategoryDto> pageableResponse = categoryservice.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}
	
	
	// single get
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
		CategoryDto categoryDto = categoryservice.getbyId(categoryId);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		
	}
	
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createcategory(@PathVariable("categoryId")String categoryId, @RequestBody ProductDto productDto ){
		ProductDto createproduct = productService.createproduct(categoryId, productDto);
		return new ResponseEntity<>(createproduct, HttpStatus.CREATED);		
	}
	
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> updateCategory(@PathVariable("categoryId") String categoryId, @PathVariable ("productId") String productId){
		ProductDto updateproduct = productService.updateproduct(productId, categoryId);
		return new ResponseEntity<>(updateproduct, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
			@PathVariable("categoryId") String categoryId,
			@RequestParam(value="pageNumber", defaultValue ="0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue ="10", required=false) int pageSize,
			@RequestParam(value="sortBy", defaultValue="title", required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir
			){
		
		PageableResponse<ProductDto> allCategory = productService.getAllCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
				return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}
	
	
}
