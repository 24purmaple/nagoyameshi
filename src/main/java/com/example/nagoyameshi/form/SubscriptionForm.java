package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubscriptionForm {
	@NotBlank(message = "カード名義人を入力してください。")
	private String cardHolderName;
	
	@NotBlank(message = "カード番号に不備があります。")
	//@NotBlank(message = "カード番号が無効です。")
	@Pattern(regexp = "\\d{16}", message = "カード番号は16桁の数字でなければなりません。")
	private String cardNumber;
	
	@NotBlank(message = "カードの有効期限の日付に不備があります。")
	@Pattern(regexp = "(0[1-9]|1[0-2])", message = "有効期限の月は01から12の間でなければなりません。")
	private String expiryMonth;
	
	@NotBlank(message = "カードの有効期限の日付に不備があります。")
	//@NotBlank(message = "カードの有効期限が過ぎています。")
	//@NotBlank(message = "カードの有効期限の日付が無効です。")
	@Pattern(regexp = "\\d{2}", message = "有効期限の年は2桁の数字でなければなりません。")
	private String expiryYear;
	
	@NotBlank(message = "セキュリティコードに不備があります。")
	@Pattern(regexp = "\\d{3,4}", message = "セキュリティコードは3桁または4桁の数字でなければなりません。")
	private String securityCode;
	
	@NotBlank(message = "郵便番号に不備があります。")
	private String postalCode;
}
