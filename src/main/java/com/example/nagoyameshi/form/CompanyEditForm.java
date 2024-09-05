package com.example.nagoyameshi.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyEditForm {
	
	@NotNull
	private Integer companyId;
	
	@NotBlank(message = "会社名を入力してください。")
	private String companyName;

	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "代表者名を入力してください。")
	private String managingDirector;

	@NotNull(message = "設立年月日を入力してください。")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate established;
	
	@NotNull(message = "資本金を入力してください。")
	@Min(value = 1, message = "資本金は1円以上に設定してください。")
	private Integer capital;
	
	@NotNull(message = "従業員数を入力してください。")
	@Min(value = 1, message = "従業員数は1名以上に設定してください。")
	private Integer employees;
	
	@NotBlank(message = "事業内容を入力してください。")
	private String service;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "正しい形式のメールアドレスを入力してください。")
	private String email;
}