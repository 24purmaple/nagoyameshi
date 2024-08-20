package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.PasswordResetToken;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.PasswordResetTokenRepository;
import com.example.nagoyameshi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PasswordResetService {
	
	private UserRepository userRepository;
	private PasswordResetTokenRepository tokenRepository;
	private PasswordEncoder passwordEncoder;
	
	public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
    public String createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);// 指定されたメールアドレスを持つユーザーをデータベースから検索します
        if (user != null) {// ユーザーが存在する場合
            String token = UUID.randomUUID().toString();// 新しいUUIDを生成して、リセットトークンを作成します
            PasswordResetToken passwordResetToken = new PasswordResetToken();// 新しいPasswordResetTokenオブジェクトを作成します
         // トークン、ユーザー、期限を設定します
            passwordResetToken.setToken(token);
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
            tokenRepository.save(passwordResetToken);// PasswordResetTokenオブジェクトをデータベースに保存します
            return token;// 生成したトークンを返します
        }
        return null;// メールアドレスに関連付けられたユーザーが存在しない場合は、nullを返す
    }

    public boolean resetPassword(String token, String newPassword) {
    	// トークンが存在し、有効期限が切れていない場合
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);
        if (passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
        	 // トークンが存在し、有効期限が切れていない場合セット保存
            User user = passwordResetToken.getUser(); 
            user.setPassword(passwordEncoder.encode(newPassword));// 新しいパスワードをエンコードしてユーザーに設定
            userRepository.save(user);
            tokenRepository.delete(passwordResetToken);// 使用済みのトークンを削除
            return true;
        }
        return false;
    }
}