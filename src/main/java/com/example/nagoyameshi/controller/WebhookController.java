package com.example.nagoyameshi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

@RestController
public class WebhookController {

    private final StripeService stripeService;
    
	// stripeのAPIを呼び出す
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
	// webhookイベントを処理するために必要
	@Value("${stripe.webhook-secret}")
	private String webhookSecret;

    public WebhookController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Stripe.apiKey = stripeApiKey;
        Event event = null;
        try {
        	// Stripeから送信されたイベントを検証して構築
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
        	// イベントの検証が失敗した場合、400 Bad Requestを返す
            return ResponseEntity.badRequest().build();
        }
       
        // セッションが完了したとき（checkout.session.completed時に）stripeServiceでデータを登録する
        if ("checkout.session.completed".equals(event.getType())) {
        	stripeService.processSessionCompleted(event);
        	
        // 請求書の支払いが成功したとき (invoice.payment_succeeded時) の処理
        } else if ("invoice.payment_succeeded".equals(event.getType())) {
        	stripeService.updateSubscription(event);
        	
        // サブスクリプションが削除されたとき (customer.subscription.deleted時) stripeServiceでデータを削除する
        } else if ("customer.subscription.deleted".equals(event.getType())) {
        	stripeService.delete(event);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}