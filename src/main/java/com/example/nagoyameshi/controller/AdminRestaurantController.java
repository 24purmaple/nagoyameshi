package com.example.nagoyameshi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.service.RestaurantService;

//管理者用
@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantService restaurantService;
	private final CategoryRepository categoryRepository;//カテゴリ
	private final RestaurantCategoryRepository restaurantCategoryRepository;
	
	public AdminRestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService, CategoryRepository categoryRepository, RestaurantCategoryRepository restaurantCategoryRepository) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantService = restaurantService;
		this.categoryRepository = categoryRepository;
		this.restaurantCategoryRepository = restaurantCategoryRepository;
	}
	
	//店舗一覧
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		Page<Restaurant> restaurantPage;
		//店舗検索
		if (keyword != null && !keyword.isEmpty()) {
			restaurantPage = restaurantRepository.findByRestaurantNameLike("%" + keyword + "%", pageable);
		} else {
			restaurantPage = restaurantRepository.findAll(pageable);
		}
		
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("keyword", keyword);
		
		return "admin/restaurants/index";
	}
	
	//店舗詳細
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		
		model.addAttribute("restaurant", restaurant);
		
		return "admin/restaurants/show";
	}
	
	//店舗登録ページへ
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("restaurantRegisterForm", new RestaurantRegisterForm());
		model.addAttribute("categories", categoryRepository.findAll()); // カテゴリデータの追加
		return "admin/restaurants/register";
	}
	
	//店舗登録
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated RestaurantRegisterForm restaurantRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());// カテゴリ一覧を追加
			return "admin/restaurants/register";
		}
		
		restaurantService.create(restaurantRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	//店舗編集へ
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		String imageName = restaurant.getImageName();
		
		List<Integer> categoryIds =restaurant.getRestaurantsCategory().stream()
				.map(rc -> rc.getCategory().getId())
				.collect(Collectors.toList());
		
		RestaurantEditForm restaurantEditForm = new RestaurantEditForm(
				restaurant.getId(),
				restaurant.getRestaurantName(), 
				null,
				restaurant.getDescription(),
				categoryIds,
				restaurant.getMinPrice(),
				restaurant.getMaxPrice(),
				restaurant.getCapacity(),
				restaurant.getOpeningTime(),
				restaurant.getClosingTime(),
				restaurant.getClosedDays(),
				restaurant.getPostalCode(),
				restaurant.getAddress(),
				restaurant.getPhoneNumber()
				);
		model.addAttribute("imageName", imageName);
		model.addAttribute("restaurantEditForm", restaurantEditForm);
		model.addAttribute("categories", categoryRepository.findAll());// カテゴリ一覧を追加
		
		return "admin/restaurants/edit";
	}
	
	//店舗更新（編集結果）
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated RestaurantEditForm restaurantEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());// カテゴリ一覧を再追加
			return "admin/restaurants/edit";
		}
		
		restaurantService.update(restaurantEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	//店舗削除
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		// レストランに関連するRestaurantCategoryを削除
	    List<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findByRestaurantId(id);
	    restaurantCategoryRepository.deleteAll(restaurantCategories);
	    
	    /*// 関連データの削除
	    reviewRepository.deleteByRestaurantId(id);
	    favoriteRepository.deleteByRestaurantId(id);
	    reservationRepository.deleteByRestaurantId(id);*/
	    
		restaurantRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を削除しました。");
		
		return "redirect:/admin/restaurants";
	}
}
