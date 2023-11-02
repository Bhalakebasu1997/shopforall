package com.store.serviceImpl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.store.Dtos.PageableResponse;
import com.store.Dtos.ProductDto;
import com.store.entities.Category;
import com.store.entities.Product;
import com.store.exception.ResourceNotFoundException;
import com.store.healpher.Helper;
import com.store.repository.CategoryRepository;
import com.store.repository.ProductRepository;
import com.store.services.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper model;
	
	@Autowired
	private CategoryRepository categoryRespository;

	private String categoryId;
	
	
	
	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = model.map(productDto, Product.class);
		String productid = UUID.randomUUID().toString();
		product.setProductId(productid);
		product.setAddedDate(new Date());
		Product save = productRepository.save(product);
		return model.map(save, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product is not found by productid !!"));
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setDiscoutedprice(productDto.getDiscoutedprice());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		product.setProductImageName(productDto.getProductImageName());
		Product saveproduct = productRepository.save(product);
		return model.map(saveproduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product is not found by productid !!"));
		productRepository.delete(product);
		
	

	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAlllive(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByLiveTrue(pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
		
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto get(String productId) {
		Product findById = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id!!"));
		return model.map(findById, ProductDto.class);
	}

	@Override
	public ProductDto createproduct(String categoryId, ProductDto productDto) {
		Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Id is not found"));
		Product product = model.map(productDto, Product.class);
		String productid = UUID.randomUUID().toString();
		product.setProductId(productid);
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product save = productRepository.save(product);
		return model.map(save, ProductDto.class);
	}

	@Override
	public ProductDto updateproduct(String productId, String categoryId) {
		Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Id is not found"));
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found of given Id!!"));
		product.setCategory(category);
		Product save = productRepository.save(product);
		return model.map(save, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Category category = categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Id is not found"));
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize , sort);
		Page<Product> page = productRepository.findByCategory(pageable, category);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	

}
