package com.store.serviceImpl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.Dtos.CategoryDto;
import com.store.Dtos.PageableResponse;
import com.store.entities.Category;
import com.store.exception.ResourceNotFoundException;
import com.store.healpher.Helper;
import com.store.repository.CategoryRepository;
import com.store.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private ModelMapper model;
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		
		String categoryid = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryid);
		
		Category category = model.map(categoryDto , Category.class);
		Category savecategory = categoryrepo.save(category);
		return model.map(savecategory, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		
		// get category of given id
		Category category = categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exception!!"));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());
		Category savecategory = categoryrepo.save(category);
		return model.map(savecategory, CategoryDto.class);
	}

	@Override
	public void delete(String categoryId) {
		
		Category category = categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exception!!"));
		
		categoryrepo.delete(category);
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int PageNumber, int pageSize,String sortBy , String sortDir) {

		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(PageNumber, pageSize, sort);	
		Page<Category> page = categoryrepo.findAll(pageable);
		PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
		return pageableResponse;
	}

//	@Override
	//public List<CategoryDto> search(String keywords) {
	
	//	List<Category> category = categoryrepo.findBytitleContaing(keywords);
	//	List<CategoryDto> collect = category.stream().map(Category -> model.map(Category, CategoryDto.class)).collect(Collectors.toList());
		//return collect ;
	//}

	@Override
	public CategoryDto getbyId(String categoryId) {
		Category category = categoryrepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exception!!"));
		return model.map(category, CategoryDto.class);
	}

}
