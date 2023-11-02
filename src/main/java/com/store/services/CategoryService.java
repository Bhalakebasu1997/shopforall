package com.store.services;

import com.store.Dtos.CategoryDto;
import com.store.Dtos.PageableResponse;

public interface CategoryService {
	
	
	// create
	CategoryDto create(CategoryDto categoryDto);
	
	// update
	
	CategoryDto update(CategoryDto categoryDto, String categoryId);
	// delete
	
	 void delete(String categoryId);
	// get all
	
	 PageableResponse<CategoryDto> getAll(int PageNumber, int pageSize, String sortBy , String sortDir);
	// get single category details 
	
	 CategoryDto getbyId(String categoryId);
	
	// search
//	List<CategoryDto> search(String keywords);
}
