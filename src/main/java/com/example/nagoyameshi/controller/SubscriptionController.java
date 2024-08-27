package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


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
	
	// 有料プランの登録処理

    
    
    //@ModelAttribute("subscriptionForm") SubscriptionForm subscriptionForm
    /*@PostMapping("/register")
    public String processSubscription(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, SubscriptionForm subscriptionForm) {
    	User user = userDetailsImpl.getUser();
    	if (user != null) {
    		//Stripeのセッションを作成
    		String sessionId = stripeService.createStripeSession(user, subscriptionForm);
    		//Stripeの決済ページにリダイレクト
    		return "redirece:https://checkout.stripe.com/pay" + sessionId;
    		/*user.setSubscriptionStartDate(LocalDate.now());//現時刻を開始日とする
    		user.setSubscriptionEndDate(LocalDate.now().plusMonths(1));//開始日から1ヶ月後を終了日とする
    		user.setRole(userService.findRoleByName("ROLE_MEMBER"));//ROLE_MEMBERにする
    		userService.save(user); // ユーザーのサブスクリプション情報を更新 /
    	}
        
        return "redirect:/subscription/success";
    }*/
    
    //有料プラン解除ページの表示
  	@GetMapping("/cancel")
  	public String cancel(Model model) {
  		return "subscription/cancel";
  	}
  	
    // 有料プラン解除メソッド
    @PostMapping("/cancel")
    public String cancelSubscription(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    	// 認証されたユーザー情報を取得
    	/*User user = userDetailsImpl.getUser();
    	
    	if (user != null) {
    		// ユーザーのサブスクリプション情報を取得
    		Subscription subscription = user.getSubscription();
    		if(subscription != null) {
    			// Stripe サービスを利用してサブスクリプションをキャンセル
    			stripeService.cancelSubscription(subscription.getStripeSubscriptionId());
    			// サブスクリプションの開始日と終了日をクリア（解除処理）
    			subscription.setSubscriptionStartDate(null);
    			subscription.setSubscriptionEndDate(null);
    			// 更新されたサブスクリプション情報をデータベースに保存
    			userService.saveSubscription(subscription);
    		}
    		user.setRole(userService.findRoleByRoleName("ROLE_GENERAL"));//ROLE_GENERALに戻す
    		userService.save(user); // ユーザーのサブスクリプション情報を更新
    	}*/
    	return "redirect:/";
    }
}
