package com.example.nagoyameshi.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final UserService userService;

    public WebhookController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Stripe.apiKey = "your-stripe-api-key"; // 環境変数または設定ファイルから取得する

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            return "Invalid signature";
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
                // セッションからメールアドレスを取得し、ユーザー情報を更新する
                String email = session.getCustomerDetails().getEmail();
                User user = userService.findByEmail(email);

                if (user != null) {
                    user.setSubscriptionStartDate(LocalDate.now());
                    user.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
                    user.setRole(userService.findRoleByRoleName("ROLE_MEMBER"));
                    userService.save(user);
                }
            }
        }

        return "success";
    }
}