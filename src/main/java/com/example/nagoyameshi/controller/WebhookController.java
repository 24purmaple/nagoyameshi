package com.example.nagoyameshi.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.service.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final UserService userService;

    public WebhookController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        String endpointSecret = "your_endpoint_secret"; // StripeのWebhookシークレット

        Event event = null;
        try {
        	// Stripeから送信されたイベントを検証して構築
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
        	// イベントの検証が失敗した場合、400 Bad Requestを返す
            return ResponseEntity.badRequest().build();
        }
        // checkout.session.completed イベントの処理
        //セッションからサブスクリプションIDと顧客IDを取得し、データベースのサブスクリプションとユーザーを更新
        if ("checkout.session_completed".equals(event.getType())) {
        	Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
            	String stripeSubscriptionId = session.getSubscription();// セッションからサブスクリプションIDを取得
            	String stripeCustomerId = session.getCustomer();// セッションから顧客IDを取得
                // 顧客IDを元に、データベースからユーザーを検索
                Subscription subscription = userService.findSubscriptionByStripeCustomerId(stripeCustomerId);                
                if (subscription != null) {
                	subscription.setStripeSubscriptionId(stripeSubscriptionId);// サブスクリプションIDを設定
                	// ユーザーのサブスクリプションの開始と終了日を更新
                    subscription.setSubscriptionStartDate(LocalDate.now());
                    subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
                    userService.saveSubscription(subscription);
                    
                    User user = subscription.getUser();
                    if(user != null) {
                    	user.setRole(userService.findRoleByRoleName("ROLE_MEMBER"));
                        userService.save(user);
                    }
                }
            }
        // invoice.payment_succeeded イベントの処理
        } else if ("invoice.payment_succeeded".equals(event.getType())) {
        	// 請求書の支払いが成功した場合に上と同様の処理
            Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
            if (invoice != null) {
            	// 請求書を支払った顧客のIDを取得
                String stripeCustomerId = invoice.getCustomer();
                // 顧客IDを元に、データベースからユーザーを検索
                Subscription subscription = userService.findSubscriptionByStripeCustomerId(stripeCustomerId);                
                if (subscription != null) {
                	// ユーザーのサブスクリプションの開始と終了日を更新
                    subscription.setSubscriptionStartDate(LocalDate.now());
                    subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
                    userService.saveSubscription(subscription);
                    
                    User user = subscription.getUser();
                    if(user != null) {
                    	user.setRole(userService.findRoleByRoleName("ROLE_MEMBER"));
                        userService.save(user);
                    }
                }
            }
         // customer.subscription.deletedイベントの処理
        } else if ("customer.subscription.deleted".equals(event.getType())) {
        	// サブスクリプションが削除された場合の処理
        	// イベントからサブスクリプションオブジェクトを取得
        	com.stripe.model.Subscription stripeSubscription = (com.stripe.model.Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
            if (stripeSubscription != null) {
            	// サブスクリプションを削除された顧客のIDを取得
                String stripeCustomerId = stripeSubscription.getCustomer();
                // 顧客IDを元に、データベースからユーザーを検索
                Subscription subscription = userService.findSubscriptionByStripeCustomerId(stripeCustomerId);
                if (subscription != null) {
                	// ユーザーのサブスクリプションの開始と終了日をnullに戻し、ロールを一般ユーザーに変更
                	subscription.setSubscriptionStartDate(null);
                	subscription.setSubscriptionEndDate(null);
                	userService.saveSubscription(subscription);
                	
                	User user = subscription.getUser();
                    if(user != null) {
                    user.setRole(userService.findRoleByRoleName("ROLE_GENERAL"));
                    userService.save(user);
                    }
                }
            }
        }

        return ResponseEntity.ok().build();
    }
}