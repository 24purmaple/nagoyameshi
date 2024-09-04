package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantService restaurantService;
	private final RestaurantRepository restaurantRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	private final CategoryRepository categoryRepository;
	
	public RestaurantController(
			RestaurantService restaurantService,
			RestaurantRepository restaurantRepository,
			ReviewRepository reviewRepository,
			ReviewService reviewService,
			FavoriteService favoriteService,
			FavoriteRepository favoriteRepository,
			CategoryRepository categoryRepository
			) {
		this.restaurantService = restaurantService;
		this.restaurantRepository = restaurantRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "category", required = false) Integer categoryId,
			@RequestParam(name = "price", required = false) Integer price,
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) 
	{
		Page<Restaurant> restaurantPage = restaurantService.searchRestaurants(keyword, null, categoryId, price, order, pageable) ;	
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("selectedCategoryId", categoryId);
		
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", categoryId);
		model.addAttribute("price", price);
		model.addAttribute("order", order);
		
		return "restaurants/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		boolean hasUserAlreadyReviewed = false;
		Favorite favorite = null;
		boolean hasUserAlreadyFavorited = false;
		
		if(userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			hasUserAlreadyReviewed = reviewService.hasUserAlreadyReviewed(restaurant, user);
			
			hasUserAlreadyFavorited = favoriteService.hasUserAlreadyFavorited(restaurant, user);
			if (hasUserAlreadyFavorited) {
				favorite = favoriteRepository.findByRestaurantAndUser(restaurant, user);
			}
		}
		
		List<Review> newReviews = reviewRepository.findTop6ByRestaurantOrderByCreatedAtDesc(restaurant);
		long totalReviewCount = reviewRepository.countByRestaurant(restaurant);
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);
		model.addAttribute("newReviews", newReviews);
		model.addAttribute("totalReviewCount", totalReviewCount);
		model.addAttribute("favorite", favorite);
		model.addAttribute("hasUserAlreadyFavorited", hasUserAlreadyFavorited);
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		
		return "restaurants/show";
	}
}