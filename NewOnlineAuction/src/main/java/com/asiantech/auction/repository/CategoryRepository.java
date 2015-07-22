package com.asiantech.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;  

import com.asiantech.auction.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	Category findByName(String categoryName);
}
