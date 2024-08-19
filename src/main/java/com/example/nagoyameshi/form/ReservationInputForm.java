package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
	@NotBlank(message = "予約したい日を選択してください。")
	private String reservationDate;
	
	@NotBlank(message = "来店する時間を選択してください。")
	private String reservationTime;
	
	@NotNull(message = "人数を入力してください。")
	@Min(value = 1, message = "人数は1人以上に設定してください。")
	private Integer numberOfPeople;
	
	// 予約日を取得する　reservaDateがnullになっていて、予約の確定時にエラーになる
    public LocalDate getParsedReservationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.reservationDate, formatter);
    }

    // 予約時間を取得する
    public LocalTime getParsedReservationTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(this.reservationTime, formatter);
    }
}