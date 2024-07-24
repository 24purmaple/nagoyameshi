package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.History;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.HistoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.HistoryService;

@Controller
public class HistoryController {
	private final HistoryRepository historyRepository;
	private final HistoryService historyService;
	private final RestaurantRepository restaurantRepository;
	
	public HistoryController(HistoryRepository historyRepository, HistoryService historyService, RestaurantRepository restaurantRepository) {
		this.historyRepository = historyRepository;
		this.historyService = historyService;
		this.restaurantRepository = restaurantRepository;
	}

	@GetMapping("/histories")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, 
	                    @PageableDefault(page = 0, size = 10, sort = "createdId") Pageable pageable, 
	                    Model model) {
		User user = userDetailsImpl.getUser();
	    Page<History> historyPage = historyRepository.findByUserOrderByCreatedAtDesc(user, pageable);
	    
	    model.addAttribute("historyPage", historyPage);
	    
	    return "histories/index";
	}
	
	@PostMapping("/restaurants/{restaurantId}/histories/create")
    public String create(@PathVariable(name = "restaurantId")Integer restaurantId,
    		@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		RedirectAttributes redirectAttributes
    		) {
		
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		User user = userDetailsImpl.getUser();
        
        historyService.create(restaurant, user);    
        
        return "redirect:/restaurants/{restaurantId}";
    }
	
	@PostMapping("/historys/{historyId}/delete")
	public String delete(@PathVariable(name = "historyId")Integer historyId, RedirectAttributes redirectAttributes) {
	    historyRepository.deleteById(historyId);
	    
	    redirectAttributes.addFlashAttribute("successMessage", "履歴から消去しました。");
	    
	    return "redirect:/histories";
	}
}