package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Company;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.CompanyEditForm;
import com.example.nagoyameshi.repository.CompanyRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.service.CompanyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	private final RestaurantRepository restaurantRepository;
	private final CompanyRepository companyRepository;
	private final CompanyService companyService;
	
	public HomeController(RestaurantRepository repositoryRestaurantRepository, CompanyRepository companyRepository, CompanyService companyService) {
		this.restaurantRepository = repositoryRestaurantRepository;
		this.companyRepository = companyRepository;
		this.companyService = companyService;
	}
	
	
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
	    List<Restaurant> newRestaurants = restaurantRepository.findTop6ByOrderByCreatedAtDesc();
	    model.addAttribute("newRestaurants", newRestaurants);
	    return "index";
	}
	
	//会社概要ページ
	@GetMapping("/company")
	public String company(Model model) {
		Company company = companyRepository.getReferenceById(1); // 会社情報を取得するサービス
	    model.addAttribute("company", company);
	    return "/company/index";
	}
	//会社概要編集ページ
	@GetMapping("/company/edit")
	public String edit(Model model) {
		Company company = companyRepository.getReferenceById(1);
			
		CompanyEditForm companyEditForm = new CompanyEditForm(
				company.getId(),
				company.getCompanyName(), 
				company.getPostalCode(),
				company.getAddress(),
				company.getManagingDirector(),
				company.getEstablished(),
				company.getCapital(),
				company.getEmployees(),
				company.getService(),
				company.getPhoneNumber(),
				company.getEmail()
				);
		
		model.addAttribute("companyEditForm", companyEditForm);
			
		return "company/edit";
	}
		
	//店舗更新（編集結果）
	@PostMapping("/company/update")
	public String update(@ModelAttribute @Validated CompanyEditForm companyEditForm, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, 
			Model model) {
		
		if (bindingResult.hasErrors()) {
	        // バリデーションエラーがある場合、再度編集ページにリダイレクト
	        return "company/edit";
	    }	
		companyService.update(companyEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "会社概要を編集しました。");
			
		return "redirect:/company";
	}
}