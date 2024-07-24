package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class RestaurantCategoryService {
	private final RestaurantCategoryRepository restaurantCategoryRepository;
	
	public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository, CategoryRepository categoryRepository) {
		this.restaurantCategoryRepository = restaurantCategoryRepository;
	}
	
	@Transactional
	public void deleteByRestaurant(Restaurant restaurant) {
		restaurantCategoryRepository.deleteByRestaurant(restaurant);
	}
	
	@Transactional
	public void deleteByCategory(Category category) {
		restaurantCategoryRepository.deleteByCategory(category);
	}
}