package com.asiantech.auction.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 

import com.asiantech.auction.entity.Category;

public interface CategoryService {
	public static String NAME = "categoryService";
	// CRUD operations
	// Save or Update
	public Category saveOrUpdate(Category category);

	// Read
	public Category getById(int id);
	public Category getByName(String name);

	// Delete
	public void deleteById(int id);

	// Get All
	public List<Category> getAll();

	// Get All and Paging
	Page<Category> getAllCategoryAndPagination(Pageable pageable); 
}
