package com.example.nagoyameshi.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.event.SignupEventPublisher;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.service.PasswordResetService;
import com.example.nagoyameshi.service.UserService;
import com.example.nagoyameshi.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	
	private final UserService userService;
	private final SignupEventPublisher signupEventPublisher;
	private final VerificationTokenService verificationTokenService;
	private final PasswordResetService passwordResetService;
	private final JavaMailSender mailSender;
	
	public AuthController(UserService userService, SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService, PasswordResetService passwordResetService, JavaMailSender mailSender) {
		this.userService = userService;
		this.signupEventPublisher = signupEventPublisher;
		this.verificationTokenService = verificationTokenService;
    	this.passwordResetService = passwordResetService;
    	this.mailSender = mailSender;
	}
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		// メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
		if (userService.isEmailRegistered(signupForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		// パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
		if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "auth/signup";
		}
		
		User createdUser = userService.create(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");
		
		return "redirect:/";
	}
	
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if(verificationToken != null){
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}
		return "auth/verify";//トップページに戻りたい
	}
	
	@GetMapping("/request_reset_password")
    public String requestResetPasswordPage() {
        return "auth/request_reset_password"; // リセット要求ページを表示
    }
	
    @PostMapping("/request_reset_password")// パスワードリセットリクエストを処理するエンドポイント
    public String requestPasswordReset(@RequestParam("email") String email, Model model) {
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
        return "auth/request_password";
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
            return "auth/reset_password";//リセットに失敗で同じページの再表示
        }
        
    }
}