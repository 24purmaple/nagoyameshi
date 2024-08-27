package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.SubscriptionRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class SubscriptionService {
	private final SubscriptionRepository subscriptionRepository;
	private final UserRepository userRepository;
	
	public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
		this.subscriptionRepository = subscriptionRepository;
		this.userRepository = userRepository;
	}
	
	//サブスクリプション情報登録機能
	@Transactional
	public Subscription create(String email, String stripeCustomerId, String stripeSubscriptionId) {
		Subscription subscription = new Subscription();
		
		// メールアドレスでユーザーを検索
		User user = userRepository.findByEmail(email);
		// サブスクリプションにユーザーとStripeのIDを設定
		subscription.setUser(user);
		subscription.setStripeCustomerId(stripeCustomerId);
		subscription.setStripeSubscriptionId(stripeSubscriptionId);
		
		return subscriptionRepository.save(subscription);
	}
	
	//サブスクリプション情報編集機能
	@Transactional
	public Subscription update(String email, String stripeCustomerId) {
		User user = userRepository.findByEmail(email);
		
		// ユーザーに関連するサブスクリプションを検索
		Subscription subscription = subscriptionRepository.findByUser(user);
		
		subscription.setUser(user);// サブスクリプションに関連付けられているユーザーを再設定
		subscription.setStripeCustomerId(stripeCustomerId);// サブスクリプションのStripe顧客IDを更新

		return subscriptionRepository.save(subscription);
	}



}