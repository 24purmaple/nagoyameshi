package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SubscriptionForm;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;


@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

	private final StripeService stripeService;
	private final UserService userService;
	
	public SubscriptionController(StripeService stripeService, UserService userService) {
		this.stripeService = stripeService;
		this.userService = userService;
	}
	
	//有料プラン登録ページの表示
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("subscriptionForm", new SubscriptionForm());
		return "subscription/register";
	}
	
	// 有料プランの登録処理
    @PostMapping("/register")
    public String processSubscription(@AuthenticationPrincipal UserDetails userDetails, SubscriptionForm subscriptionForm) {
        User user = userService.findByEmail(userDetails.getUsername());
    	if (user != null) {
    		//Stripeのセッションを作成
    		String sessionId = stripeService.createStripeSession(user, subscriptionForm);
    		//Stripeの決済ページにリダイレクト
    		return "redirece:https://checkout.stripe.com/pay" + sessionId;
    		/*user.setSubscriptionStartDate(LocalDate.now());//現時刻を開始日とする
    		user.setSubscriptionEndDate(LocalDate.now().plusMonths(1));//開始日から1ヶ月後を終了日とする
    		user.setRole(userService.findRoleByName("ROLE_MEMBER"));//ROLE_MEMBERにする
    		userService.save(user); // ユーザーのサブスクリプション情報を更新 */
    	}
        
        return "redirect:/subscription/success";
    }
    
    // 有料プラン解除メソッド
    @PostMapping("/cancel")
    public String cancelSubscription(@AuthenticationPrincipal UserDetails userDetails) {
    	User user = userService.findByEmail(userDetails.getUsername());
    	if (user != null) {
    		stripeService.cancelSubscription(user);
    		user.setSubscriptionStartDate(null);
    		user.setSubscriptionEndDate(null);
    		user.setRole(userService.findRoleByRoleName("ROLE_GENERAL"));//ROLE_GENERALに戻す
    		userService.save(user); // ユーザーのサブスクリプション情報を更新
    	}
    	return "redirect:/subscription/canceled";
    }    
}
