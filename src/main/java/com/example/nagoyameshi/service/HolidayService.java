package com.example.nagoyameshi.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    // 固定祝日リストを取得
    public List<LocalDate> getFixedHolidays(int year) {
        return Arrays.asList(
            LocalDate.of(year, 1, 1),  // 元日
            LocalDate.of(year, 2, 11), // 建国記念の日
            LocalDate.of(year, 2, 23), // 天皇誕生日
            LocalDate.of(year, 4, 29), // 昭和の日
            LocalDate.of(year, 5, 3), // 憲法記念日
            LocalDate.of(year, 5, 4), // みどりの日
            LocalDate.of(year, 5, 5), // こどもの日
            LocalDate.of(year, 8, 11), // 山の日
            LocalDate.of(year, 11, 3), // 文化の日
            LocalDate.of(year, 11, 23) // 勤労感謝の日
            // 他の固定祝日も追加可能
        );
    }

    // 春分の日を計算
    public LocalDate getSpringEquinox(int year) {
        int day;
        if (year >= 1900 && year <= 2099) {
            day = (int) (20.8431 + 0.242194 * (year - 1980) - (year - 1980) / 4);
        } else if (year >= 2100 && year <= 2150) {
            day = (int) (21.851 + 0.242194 * (year - 1980) - (year - 1980) / 4);
        } else {
            throw new IllegalArgumentException("計算範囲外の年です。");
        }
        return LocalDate.of(year, Month.MARCH, day);
    }

    // 秋分の日を計算
    public LocalDate getAutumnEquinox(int year) {
        int day;
        if (year >= 1900 && year <= 2099) {
            day = (int) (23.2488 + 0.242194 * (year - 1980) - (year - 1980) / 4);
        } else if (year >= 2100 && year <= 2150) {
            day = (int) (24.2488 + 0.242194 * (year - 1980) - (year - 1980) / 4);
        } else {
            throw new IllegalArgumentException("計算範囲外の年です。");
        }
        return LocalDate.of(year, Month.SEPTEMBER, day);
    }
    
    // 移動祝日を一括チェックする
    public boolean isMovingHoliday(LocalDate date) {
    	// 例: 成人の日 (1月の第2月曜日)
        if (date.getMonth() == Month.JANUARY 
            && date.getDayOfWeek() == DayOfWeek.MONDAY 
            && date.getDayOfMonth() > 7 
            && date.getDayOfMonth() <= 14) {
            return true;
        }
        
        // 例: 海の日 (7月の第3月曜日)
        if (date.getMonth() == Month.JULY 
            && date.getDayOfWeek() == DayOfWeek.MONDAY 
            && date.getDayOfMonth() > 14 
            && date.getDayOfMonth() <= 21) {
            return true;
        }
        
    	// 敬老の日 (9月の第3月曜日)
        if (date.getMonth() == Month.SEPTEMBER 
            && date.getDayOfWeek() == DayOfWeek.MONDAY 
            && date.getDayOfMonth() > 14 
            && date.getDayOfMonth() <= 21) {
            return true;
        }

        // 例: スポーツの日 (10月の第2月曜日)
        if (date.getMonth() == Month.JULY 
            && date.getDayOfWeek() == DayOfWeek.MONDAY 
            && date.getDayOfMonth() > 14 
            && date.getDayOfMonth() <= 21) {
            return true;
        }

        // 他の移動祝日もここで追加可能
        // 春分の日・秋分の日チェック
        if (date.equals(getSpringEquinox(date.getYear())) || date.equals(getAutumnEquinox(date.getYear()))) {
            return true;
        }

        return false;
    }
}
