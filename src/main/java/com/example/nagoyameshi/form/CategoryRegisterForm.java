package com.example.nagoyameshi.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRegisterForm {

	@NotBlank(message = "登録するカテゴリ名を入力してください。")
	@Length(max = 50, message = "カテゴリは50文字以内で入力してください。")
	private String categoryName;
}