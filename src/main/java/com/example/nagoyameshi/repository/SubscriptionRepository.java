package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    public Subscription findByStripeCustomerId(String stripeCustomerId);
    
}
