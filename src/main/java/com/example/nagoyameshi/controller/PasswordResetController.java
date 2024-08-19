/*package com.example.nagoyameshi.controller;

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

    @PostMapping("/request_reset_password")// パスワードリセットリクエストを処理するエンドポイント
    public String RequestPasswordReset(@RequestParam("email") String email, Model model) {
        String token = passwordResetService.createPasswordResetToken(email);// 指定されたメールアドレスに対して、パスワードリセットトークンを生成します
        if (token != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);//送信先
            message.setSubject("パスワードリセット");//メールの件名
            message.setText("以下のリンクをクリックしてパスワードをリセットしてください:\n" 
                            + "http://localhost:8080/reset-password?token=" + token);//メール本文、リンクを添えて
            mailSender.send(message);//メールの送信
            model.addAttribute("message", "パスワードリセットリンクを送信しました。");
        } else {
            model.addAttribute("error", "メールアドレスが見つかりませんでした。");
        }
        return "request_password";
    }
    
    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                Model model) {
    	// トークンと新しいパスワードを使用してパスワードをリセットします
        boolean result = passwordResetService.resetPassword(token, password);
        if (result) {
            model.addAttribute("message", "パスワードがリセットされました。");
            return "redirect:/";//リセット成功でトップページへ
        } else {
            model.addAttribute("error", "トークンが無効または期限切れです。");
            return "reset_password";//リセットに失敗で同じページの再表示
        }
        
    }
}*/