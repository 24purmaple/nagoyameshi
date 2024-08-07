package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEditForm {
	@NotNull
	private Integer Id;
	
	@NotBlank(message = "氏名を入力してください。")
	private String userName;
	
	@NotBlank(message = "フリガナを入力してください。")
	private String furigana;

	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスは正しい形式で入力してください。")
	private String email;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
}