package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final RestaurantCategoryService restaurantCategoryService;
	
	public CategoryService(CategoryRepository categoryRepository, RestaurantCategoryService restaurantCategoryService) {
		this.categoryRepository = categoryRepository;
		this.restaurantCategoryService = restaurantCategoryService;
	}
	
	@Transactional
	public void create(CategoryRegisterForm categoryRegisterForm) {
		Category category = new Category();
		
		category.setCategoryName(categoryRegisterForm.getCategoryName());
		
		categoryRepository.save(category);
	}
	
	@Transactional
	public void update(CategoryEditForm categoryEditForm) {
		Category category = categoryRepository.getReferenceById(categoryEditForm.getCategoryId());
		
		category.setCategoryName(categoryEditForm.getCategoryName());
		
		categoryRepository.save(category);
	}

	@Transactional
	public void delete(Category category) {
		restaurantCategoryService.deleteByCategory(category);
		categoryRepository.delete(category);
	}
}