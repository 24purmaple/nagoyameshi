package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	public Subscription findByUser(User user);
    public Subscription findByStripeCustomerId(String stripeCustomerId);
    public Subscription findByStripeSubscriptionId(String stripeSubscriptionId);
    
    @Transactional
	public Integer deleteByStripeSubscriptionId(String stripeSubscriptionId);

}
