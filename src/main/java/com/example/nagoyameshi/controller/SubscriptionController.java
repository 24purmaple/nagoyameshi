package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SubscriptionForm;
import com.example.nagoyameshi.security.UserDetailsImpl;
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
	@GetMapping("/confirm")
	public String confirm(Model model) {
		model.addAttribute("subscriptionForm", new SubscriptionForm());
		return "subscription/confirm";
	}
	
	// 有料プランの登録処理
    @PostMapping("/confirm")
    public String confirm(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    		@ModelAttribute("subscriptionForm") SubscriptionForm subscriptionForm
    		) {
    	//ログイン中のユーザーの情報を取得
        User user = userDetailsImpl.getUser();
        if (user != null) {
        	//Stripeのセッションを作成し、セッションIDを取得
        	String sessionId = stripeService.createStripeSession(user, subscriptionForm);

        	//Stripeの決済ページにリダイレクト
        	return "redirect:https://checkout.stripe.com/pay" + sessionId;
        }
        return "redirect:/subscription/success";
    }
    
    

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
    	User user = userDetailsImpl.getUser();
    	if (user != null) {
    		stripeService.cancelSubscription(user);
    		//エラー解決案
    		//1.Userエンティティにフィールドを追加する
    		//2.サブスクリプションIDの取得: cancelSubscription メソッド内で、User エンティティからサブスクリプションIDを取得する
    		//3.サービスクラスでの操作: stripeService.cancelSubscription() メソッド内で、受け取ったサブスクリプションIDを使ってStripeのAPIを呼び出し、キャンセル操作を行う
    		user.setSubscriptionStartDate(null);
    		user.setSubscriptionEndDate(null);
    		user.setRole(userService.findRoleByRoleName("ROLE_GENERAL"));//ROLE_GENERALに戻す
    		userService.save(user); // ユーザーのサブスクリプション情報を更新
    	}
    	return "redirect:/subscription/canceled";
    }    
}
