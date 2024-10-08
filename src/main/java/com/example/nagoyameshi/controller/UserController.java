package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.PasswordEditForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
	private final UserRepository userRepository;
	private final UserService userService;
	
	public UserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}
	//会員情報へ
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		/*ユーザーが見つからない場合のエラーハンドリング
		if(user == null) {
			model.addAttribute("errorMessage", "ユーザーが見つかりません。");
			return "user/error";エラーページを表示
		}*/
		
		model.addAttribute("user", user);
		
		return "user/index";
	}
	//会員情報編集
	@GetMapping("/edit")
	public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user =userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		UserEditForm userEditForm = new UserEditForm(
				user.getId(),
				user.getUserName(),
				user.getFurigana(),
				user.getEmail(),
				user.getPhoneNumber());
		
		model.addAttribute("userEditForm", userEditForm);
		
		return "user/edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		// メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
		if(userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		if(bindingResult.hasErrors()) {
			return "user/edit";
		}
		
		userService.update(userEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
		
		return "redirect:/user";
	}
	
	// 退会確認ページの表示
	@GetMapping("/delete")
	public String deleteConfirm(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		model.addAttribute("user", user);
		
		return "user/deleteConfirm";
	}
	
	//退会処理
	@PostMapping("/delete")
	public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		userService.delete(user);
		redirectAttributes.addFlashAttribute("successMessage", "退会処理が完了しました。");
		
		return "redirect:/";
	}
	
	//パスワード編集
	@GetMapping("/password_edit")
	public String passwordEdit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user =userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		PasswordEditForm passwordEditForm = new PasswordEditForm(null, null, null, null);
		passwordEditForm.setId(user.getId());
		passwordEditForm.setNewPassword(null);
		passwordEditForm.setNewPasswordConfirmation(null);
		passwordEditForm.setOldPassword(null);
		
		model.addAttribute("passwordEditForm", passwordEditForm);
		
		return "user/password_edit";
	}
		
	@PostMapping("/password_update")
	public String passwordUpdate(@ModelAttribute @Validated PasswordEditForm passwordEditForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		// 旧パスワードと現在のパスワードが一致するかをチェックする
	    if (!userService.isCorrectOldPassword(passwordEditForm.getOldPassword(), user)) {
	        FieldError fieldError = new FieldError(bindingResult.getObjectName(), "oldPassword", "旧パスワードが正しくありません。");
	        bindingResult.addError(fieldError);
	    }
		
		// パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
		if (!userService.isSamePassword(passwordEditForm.getNewPassword(), passwordEditForm.getNewPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "newPasswordConfirmation", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		if(bindingResult.hasErrors()) {
			return "user/password_edit";
		}
		
		userService.updatePassword(user, passwordEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "パスワードを変更しました。");
		
		return "redirect:/user";
	}
}