package com.example.nagoyameshi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.PasswordEditForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	//↓コンストラクタ
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;	//←フィールド
		this.roleRepository = roleRepository;
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
		User user = userRepository.getReferenceById(userEditForm.getId());
		
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
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}
	
	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}
	

	//サブスク用に追加
    @Transactional
    public void roleUpgrade(String email) {
    	User user = userRepository.findByEmail(email);
    	Role role = roleRepository.getReferenceById(2);
    	
    	user.setRole(role);
    	
        userRepository.save(user);
    }
    
    @Transactional
    public void roleDowngrade(String email) {
    	User user = userRepository.findByEmail(email);
    	Role role = roleRepository.getReferenceById(1);
    	
    	user.setRole(role);
    	
        userRepository.save(user);
    }
    
    // 現在のパスワード（確認用）と現在のパスワードの入力値が一致するかどうかをチェックする
 	public boolean isCorrectOldPassword(String oldPassword, User user) {
 		return passwordEncoder.matches(oldPassword, user.getPassword());
 	}

	public void updatePassword(User user, PasswordEditForm passwordEditForm) {
		
		// 新しいパスワードを設定
	    user.setPassword(passwordEncoder.encode(passwordEditForm.getNewPassword()));
		
		userRepository.save(user);
	}
}