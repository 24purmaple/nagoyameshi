package com.example.nagoyameshi.service;

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
	
	public ReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Integer restaurantId = Integer.valueOf(reservationRegisterForm.getRestaurantId());
		Integer userId = Integer.valueOf(reservationRegisterForm.getUserId());
		
		Reservation reservation = new Reservation();
		Restaurant restaurant =restaurantRepository.getReferenceById(restaurantId);
		User user = userRepository.getReferenceById(userId);
		//LocalDate reservationDate = LocalDate.parse(reservationRegisterForm.getReservationDate());
		//LocalTime reservationTime = LocalTime.parse(reservationRegisterForm.getReservationTime());
		Integer numberOfPeople = Integer.valueOf(reservationRegisterForm.getNumberOfPeople());
		
		reservation.setRestaurant(restaurant);
		reservation.setUser(user);
		//reservation.setReservationDate(reservationDate);
		//reservation.setReservationTime(reservationTime);
		reservation.setNumberOfPeople(numberOfPeople);
		
		reservationRepository.save(reservation);
	}
	
	// 来店人数が定員以下かどうかをチェックする
	public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
		return numberOfPeople <= capacity;
	}
	
	/* 予約時間と予約日が営業時間外、もしくは定休日の場合を考えたが定休日を曜日で管理していないため使用できない
	 * public static String isWithinTime(Restaurant restaurant, ReservationInputForm reservationInput) {
	    // 予約日と予約時間を取得
	    LocalDate reservationDate = reservationInput.getParsedReservationDate();
	    LocalTime reservationTime = reservationInput.getParsedReservationTime();

	    // 定休日のチェック
	    // 予約日の曜日を取得
	    DayOfWeek reservationDayOfWeek = reservationDate.getDayOfWeek();

	    // 定休日が曜日で管理されている場合のチェック
	    if (restaurant.getClosedDays().contains(reservationDayOfWeek.toString())) {
	        return "エラー: 予約日はレストランの定休日です。 (" + reservationDayOfWeek.toString() + ")";
	    }

	    // 営業時間内のチェック
	    LocalTime openingTime = restaurant.getOpeningTime();
	    LocalTime closingTime = restaurant.getClosingTime();

	    // 予約時間が開店時間より前または閉店時間と同じか後の場合、エラーメッセージを返す
	    if (reservationTime.isBefore(openingTime) || !reservationTime.isBefore(closingTime)) {
	        return "エラー: 予約時間がレストランの営業時間外です。";
	    }

	    // 予約時間が有効であることを示すメッセージを返す
	    return "予約時間は有効です。";
	}*/
}