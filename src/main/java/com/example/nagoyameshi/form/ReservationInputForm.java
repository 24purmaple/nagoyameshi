package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    
    public LocalDate getParsedReservationDate() {
        if (this.reservationDate == null || this.reservationDate.trim().isEmpty()) {
            return null; // 入力が空の場合は null を返す
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(this.reservationDate, formatter);
        } catch (DateTimeParseException e) {
            return null; // 解析できない場合も null を返す
        }
    }

    public LocalTime getParsedReservationTime() {
        if (this.reservationTime == null || this.reservationTime.trim().isEmpty()) {
            return null; // 入力が空の場合は null を返す
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(this.reservationTime, formatter);
        } catch (DateTimeParseException e) {
            return null; // 解析できない場合も null を返す
        }
    }
}