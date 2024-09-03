package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.SubscriptionRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

	private final StripeService stripeService;
	private final UserService userService;
	private final SubscriptionRepository subscriptionRepository;
	
	public SubscriptionController(StripeService stripeService, UserService userService, SubscriptionRepository subscriptionRepository) {
		this.stripeService = stripeService;
		this.userService = userService;
		this.subscriptionRepository = subscriptionRepository;
	}
	
	//有料プラン登録ページの表示
	@GetMapping("/confirm")
	public String confirm(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		HttpServletRequest httpServletRequest,
    		Model model) {
    	//ログイン中のユーザーの情報を取得
        User user = userDetailsImpl.getUser();
        if (user != null) {
        	//Stripeのセッションを作成し、セッションIDを取得
        	String sessionId = stripeService.createStripeSession(user, httpServletRequest);
        	
        	// セッションIDをモデルに追加
            model.addAttribute("sessionId", sessionId);
        	
        	//Stripeの決済ページにリダイレクト
        	return "subscription/confirm";
        }
        return "redirect:/subscription/success";
	}
    
    //有料プラン解除ページの表示
  	@GetMapping("/cancel")
  	public String cancel(Model model) {
  		return "subscription/cancel";
  	}
  	
    // 有料プラン解除メソッド
    @PostMapping("/cancel")
    public String cancelSubscription(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
    	// 認証されたユーザー情報を取得
    	User user = userDetailsImpl.getUser();
    	String email = user.getEmail();
    	if (user != null) {
    		// ユーザーに関連するサブスクリプションを検索
    		Subscription subscription = subscriptionRepository.findByUser(user);
    		if(subscription != null) {
    			// Stripe サービスを利用してサブスクリプションをキャンセル
    			stripeService.cancelSubscription(subscription.getStripeSubscriptionId());
    		}
    		userService.roleDowngrade(email);
    	}
    	
    	// 成功メッセージを追加
        redirectAttributes.addFlashAttribute("successMessage", "有料プランをキャンセルしました。");
        
    	return "redirect:/";
    }
}
