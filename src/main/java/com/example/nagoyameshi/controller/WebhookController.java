package com.example.nagoyameshi.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.service.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.Subscription;
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
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }

        if ("invoice.payment_succeeded".equals(event.getType())) {
            Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
            if (invoice != null) {
                String customerId = invoice.getCustomer();
                User user = userService.findByStripeCustomerId(customerId);
                if (user != null) {
                    user.setSubscriptionStartDate(LocalDate.now());
                    user.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
                    user.setRole(userService.findRoleByRoleName("ROLE_MEMBER"));
                    userService.save(user);
                }
            }
        } else if ("customer.subscription.deleted".equals(event.getType())) {
            Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
            if (subscription != null) {
                String customerId = subscription.getCustomer();
                User user = userService.findByStripeCustomerId(customerId);
                if (user != null) {
                    user.setSubscriptionStartDate(null);
                    user.setSubscriptionEndDate(null);
                    user.setRole(userService.findRoleByRoleName("ROLE_GENERAL"));
                    userService.save(user);
                }
            }
        }

        return ResponseEntity.ok().build();
    }
}