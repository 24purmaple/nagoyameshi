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
}