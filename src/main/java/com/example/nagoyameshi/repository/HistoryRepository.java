package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.History;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;

public interface HistoryRepository extends JpaRepository<History, Integer> {
	public History findByRestaurantAndUser(Restaurant restaurant, User user);
	public Page<History>findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}