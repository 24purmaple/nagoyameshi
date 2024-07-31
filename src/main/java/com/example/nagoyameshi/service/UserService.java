package com.example.nagoyameshi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.Subscription;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.SubscriptionRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PasswordEncoder passwordEncoder;
	
	//↓コンストラクタ
	public UserService(UserRepository userRepository, RoleRepository roleRepository, SubscriptionRepository subscriptionRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;	//←フィールド
		this.roleRepository = roleRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByRoleName("ROLE_GENERAL");
		
		user.setUserName(signupForm.getUserName());
		user.setFurigana(signupForm.getFurigana());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		user.setEnabled(false);	//メール認証済みかどうかの判定に利用する
		
		return userRepository.save(user);
	}
	
	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getUserId());
		
		user.setUserName(userEditForm.getUserName());
		user.setFurigana(userEditForm.getFurigana());
		user.setEmail(userEditForm.getEmail());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		
		userRepository.save(user);
	}
	
	// メールアドレスが登録済みかどうかをチェックする
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
	// パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
	
	// ユーザーを有効にする
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	// メールアドレスが変更されたかどうかをチェックする
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getUserId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}
	
	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}
	

	//サブスク用に追加
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
    
    @Transactional
	public void saveSubscription(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}

	public Role findRoleByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

	public Subscription findSubscriptionByStripeCustomerId(String stripeCustomerId) {
        return subscriptionRepository.findByStripeCustomerId(stripeCustomerId);
    }
	
	public User registerUser(User user) {
        // ユーザーをデータベースに保存する前に、Stripe顧客を作成
        String stripeCustomerId = createStripeCustomer(user);
        Subscription subscription = new Subscription();
        subscription.setStripeCustomerId(stripeCustomerId);
        user.setSubscription(subscription);
        // ユーザーをデータベースに保存
        return userRepository.save(user);
    }
    
    private String createStripeCustomer(User user) {
        // Stripe APIを使用して顧客を作成
        Stripe.apiKey = "your-stripe-api-key";
        
        Map<String, Object> params = new HashMap<>();
        params.put("email", user.getEmail());
        params.put("name", user.getUserName());
        
        try {
            Customer customer = Customer.create(params);
            return customer.getId();
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Stripe顧客の作成に失敗しました");
        }
    }

}