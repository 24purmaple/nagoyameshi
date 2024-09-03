package com.example.nagoyameshi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column(name = "stripe_customer_id")
	private String stripeCustomerId;// Stripeで顧客を識別するためのID、stripeから提供される
	
	@Column(name = "stripe_subscription_id")
	private String stripeSubscriptionId; // Stripeでサブスクリプションのプランを識別するためのID（現状1種なので全て同じ値）
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User user;
}
