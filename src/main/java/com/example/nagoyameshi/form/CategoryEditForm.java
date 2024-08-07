package com.example.nagoyameshi.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryEditForm {

	@NotNull
	private Integer id;
	
	@NotBlank(message = "登録するカテゴリ名を入力してください。")
	@Length(max = 50, message = "カテゴリは50文字以内で入力してください。")
	private String categoryName;
}