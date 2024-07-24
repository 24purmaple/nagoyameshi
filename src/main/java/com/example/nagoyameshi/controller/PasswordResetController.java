package com.example.nagoyameshi.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.service.PasswordResetService;

@Controller
public class PasswordResetController {

    private PasswordResetService passwordResetService;
    private JavaMailSender mailSender;
    
    public PasswordResetController(PasswordResetService passwordResetService, JavaMailSender mailSender) {
    	this.passwordResetService = passwordResetService;
    	this.mailSender = mailSender;
    }

    @PostMapping("/request_reset_password")
    public String handlePasswordReset(@RequestParam("email") String email, Model model) {
        String token = passwordResetService.createPasswordResetToken(email);
        if (token != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("パスワードリセット");
            message.setText("以下のリンクをクリックしてパスワードをリセットしてください:\n" 
                            + "http://localhost:8080/reset-password?token=" + token);
            mailSender.send(message);
            model.addAttribute("message", "パスワードリセットリンクを送信しました。");
        } else {
            model.addAttribute("error", "メールアドレスが見つかりませんでした。");
        }
        return "reset_password_request";
    }
    
    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                Model model) {
        boolean result = passwordResetService.resetPassword(token, password);
        if (result) {
            model.addAttribute("message", "パスワードがリセットされました。");
        } else {
            model.addAttribute("error", "トークンが無効または期限切れです。");
        }
        return "reset_password";
    }
}