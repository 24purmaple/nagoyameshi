package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public Page<Category> findByCategoryNameLike(String keyword, Pageable pageable);
	public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);
}