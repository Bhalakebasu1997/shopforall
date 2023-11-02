package com.store.services;

import com.store.Dtos.PageableResponse;
import com.store.Dtos.ProductDto;

public interface ProductService {
	
	
	// create
	ProductDto create(ProductDto productDto);
	
	// update
	
	ProductDto update(ProductDto productDto, String productId);
	
	// delete
	
	void delete(String productId);
	// get all
	
	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);	
	// get by single id 
	
	// live
	PageableResponse<ProductDto> getAlllive(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// search product
	PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);
	
	
	// get id 
	ProductDto get(String productId);
	
	
	// product create with category
	ProductDto createproduct(String categoryId, ProductDto productDto);

	//update the product of the category 
	ProductDto updateproduct(String productId, String categoryId);
	
	// get the product details through category Id 
	PageableResponse<ProductDto> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
	
	
	
}
