package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Controller
public class HomeController {
	private final RestaurantRepository restaurantRepository;
	
	public HomeController(RestaurantRepository repositoryRestaurantRepository) {
		this.restaurantRepository = repositoryRestaurantRepository;
	}
	
	
	@GetMapping("/")
	public String index(Model model) {
		List<Restaurant> newRestaurants = restaurantRepository.findTop6ByOrderByCreatedAtDesc();
		model.addAttribute("newRestaurants", newRestaurants);
		return "index";
	}

}