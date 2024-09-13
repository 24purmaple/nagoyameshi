package com.example.nagoyameshi.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RestaurantRepository restaurantRepository;
	private final UserRepository userRepository;
	private final HolidayService holidayService;
	
	public ReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, HolidayService holidayService) {
		this.reservationRepository = reservationRepository;
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
		this.holidayService = holidayService;
	}
	
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Integer restaurantId = Integer.valueOf(reservationRegisterForm.getRestaurantId());
		Integer userId = Integer.valueOf(reservationRegisterForm.getUserId());
		
		Reservation reservation = new Reservation();
		Restaurant restaurant =restaurantRepository.getReferenceById(restaurantId);
		User user = userRepository.getReferenceById(userId);
		LocalDate reservationDate = reservationRegisterForm.getReservationDate();
		LocalTime reservationTime = reservationRegisterForm.getReservationTime();
		Integer numberOfPeople = Integer.valueOf(reservationRegisterForm.getNumberOfPeople());
		
		reservation.setRestaurant(restaurant);
		reservation.setUser(user);
		reservation.setReservationDate(reservationDate);
		reservation.setReservationTime(reservationTime);
		reservation.setNumberOfPeople(numberOfPeople);
		
		reservationRepository.save(reservation);
	}
	
	// 来店人数が定員以下かどうかをチェックする
	public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
		return numberOfPeople <= capacity;
	}

	// 営業時間チェック
	public boolean isWithinBusinessHours(Restaurant restaurant, LocalTime reservationTime) {
	    //レストランの開店時間と閉店時間を取得
	    LocalTime openingTime = restaurant.getOpeningTime();
	    LocalTime closingTime = restaurant.getClosingTime();
	    // 閉店時間が開店時間より前の場合は、営業時間が夜をまたぐ
	    boolean isOvernight = closingTime.isBefore(openingTime);

	    if (isOvernight) {
	    	// 夜をまたぐ営業時間の場合、開店時間以降または閉店時間以前なら営業中
	        return reservationTime.isAfter(openingTime) || reservationTime.isBefore(closingTime);
	    } else {
	    	// 通常の営業時間の場合、開店時間以降かつ閉店時間以前なら営業中
	        return !reservationTime.isBefore(openingTime) && !reservationTime.isAfter(closingTime);
	    }
	}

	// 定休日チェック
	public boolean isNotHoliday(Restaurant restaurant, LocalDate reservationDate) {
		// 予約日の曜日を取得し、その文字列表現を取得
		DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();
	    String dayString = convertDayOfWeekToString(dayOfWeek);

	    // レストランの定休日リストにその日が含まれているか確認
	    boolean isHoliday = restaurant.getClosedDays().contains(dayString);

	    // 祝日もチェック
	    if (isPublicHoliday(reservationDate)) {
	        isHoliday = isHoliday || restaurant.getClosedDays().contains("祝日");
	    }

	    // 定休日でなければtrue
	    return !isHoliday;
	}

	//getDayOfWeekで取得した曜日(MONDAYなど)の情報を変換する
	private String convertDayOfWeekToString(DayOfWeek dayOfWeek) {
	    switch (dayOfWeek) {
	        case MONDAY: return "月曜日";
	        case TUESDAY: return "火曜日";
	        case WEDNESDAY: return "水曜日";
	        case THURSDAY: return "木曜日";
	        case FRIDAY: return "金曜日";
	        case SATURDAY: return "土曜日";
	        case SUNDAY: return "日曜日";
	        default: return "";
	    }
	}

	private boolean isPublicHoliday(LocalDate date) {
	    // 祝日判定ロジック
		int year = date.getYear();
		//固定祝日
		if(holidayService.getFixedHolidays(year).contains(date)) {
			return true;
		}
		//移動祝日
		if(holidayService.isMovingHoliday(date)) {
			return true;
		}
	    return false;
	}
	
	public boolean isOpenAllYear(Restaurant restaurant) {
	    return restaurant.getClosedDays() == null || restaurant.getClosedDays().isEmpty();
	}

}