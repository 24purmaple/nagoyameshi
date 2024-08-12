package com.example.nagoyameshi.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "旧パスワードを入力してください")
	private String oldPassword;
	
	@NotBlank(message = "新しいパスワードを入力してください。")
	@Length(min = 8, message = "パスワードは8文字以上で入力してください。")
	private String newPassword;
	
	@NotBlank(message = "新しいパスワード（確認用）を入力してください。")
	private String newPasswordConfirmation;
}
