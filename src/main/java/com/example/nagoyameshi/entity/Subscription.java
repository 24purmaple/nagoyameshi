package com.example.nagoyameshi.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;// データベース上でサブスクリプションを識別するためのID、エンティティ作成時にできる
	
	@Column(name = "subscription_start_date")
	private LocalDate subscriptionStartDate;
    
	@Column(name = "subscription_end_date")
    private LocalDate subscriptionEndDate;
	
	@Column(name = "stripe_customer_id")
	private String stripeCustomerId;// Stripeで顧客を識別するためのID、stripeから提供される
	
	@Column(name = "stripe_subscription_id")
	private String stripeSubscriptionId; // Stripeでサブスクリプションのプランを識別するためのID（現状1種なので全て同じ値）
	
	@OneToOne(mappedBy = "subscription")
    private User user;
}
