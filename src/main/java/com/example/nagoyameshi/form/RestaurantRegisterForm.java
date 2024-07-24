package com.example.nagoyameshi.form;

import java.time.LocalTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRegisterForm {
	
	@NotBlank(message = "店名を入力してください。")
	private String restaurantName;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "説明を入力してください。")
	private String description;
	
	@NotNull(message = "カテゴリを選択してください。")
    private List<Integer> categoryIds;
	
	@NotNull(message = "最小料金を入力してください。")
	@Min(value = 1, message = "最小料金は1円以上に設定してください。")
	private Integer minPrice;
	
	@NotNull(message = "最高料金を入力してください。")
	@Max(value = 999999, message = "最大料金は999999円以下に設定してください。")
	private Integer maxPrice;
	
	/*
	@NotNull(message = "料金目安を入力してください。")
	@Range(min = 1, max = 999999, message = "料金目安は1円以上999999円以下に設定してください。")
	private Integer Price;
	*/
	
	@NotNull(message = "定員を入力してください。")
	@Min(value = 1, message = "定員は1人以上に設定してください。")
	private Integer capacity;
	
	@NotNull(message = "開店時間を入力してください。")
	private LocalTime openingTime;
	
	@NotNull(message = "閉店時間を入力してください。")
	private LocalTime closingTime;
	
	@NotBlank(message = "定休日を入力してください。")
	private String closedDays;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
}