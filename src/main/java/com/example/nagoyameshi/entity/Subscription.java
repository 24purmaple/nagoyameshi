package com.example.nagoyameshi.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Subscription {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;// データベース上でサブスクリプションを識別するためのID
	
	@Column(name = "subscription_start_date")
	private LocalDate subscriptionStartDate;
    
	@Column(name = "subscription_END_date")
    private LocalDate subscriptionEndDate;
	
	private String stripeCustomerId;// Stripeで顧客を識別するためのID
	private String stripeSubscriptionId; // Stripeでサブスクリプションを識別するためのID
	
	@OneToOne(mappedBy = "subscription")
    private User user;
}
