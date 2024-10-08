package com.example.nagoyameshi.form;

import java.time.LocalTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {
	
	@NotNull
	private Integer restaurantId;
	
	@NotBlank(message = "店名を入力してください。")
	private String restaurantName;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "説明を入力してください。")
	private String description;

	@NotEmpty(message = "カテゴリを選択してください。")
    private List<Integer> categoryIds;

	@NotNull(message = "最小料金を入力してください。")
	@Min(value = 1, message = "最小料金は1円以上に設定してください。")
	private Integer minPrice;
	
	@NotNull(message = "最大料金を入力してください。")
	@Max(value = 999999, message = "最大料金は999999円以下に設定してください。")
	private Integer maxPrice;
	
	@NotNull(message = "定員を入力してください。")
	@Min(value = 1, message = "定員は1人以上に設定してください。")
	private Integer capacity;
	
	@NotNull(message = "開店時間を入力してください。")
	private LocalTime openingTime;
	
	@NotNull(message = "閉店時間を入力してください。")
	private LocalTime closingTime;
	
	@NotEmpty(message = "定休日を選択してください。")
	private List<String> closedDays;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
}