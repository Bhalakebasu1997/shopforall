package com.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.store.entities.Category;
import com.store.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

	Page<Product> findByLiveTrue(Pageable pageable);
	Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
	Page<Product> findByCategory(Pageable pageable, Category category);
	
}
