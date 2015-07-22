package com.asiantech.auction.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asiantech.auction.entity.Category;
import com.asiantech.auction.repository.CategoryRepository;

@Service(CategoryService.NAME)
public class CategoryServiceImpl implements CategoryService{
	@Resource
	CategoryRepository categoryRepository;

	@Override
	public Category saveOrUpdate(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category getById(int id) {
		return categoryRepository.findOne(id);
	}
	
	@Override
	public Category getByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public void deleteById(int id) {
		categoryRepository.delete(id);
	}

	@Override
	public List<Category> getAll() { 
		return categoryRepository.findAll();
	}

	@Override
	public Page<Category> getAllCategoryAndPagination(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}
	
}
