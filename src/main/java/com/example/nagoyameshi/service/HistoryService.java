package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.History;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.HistoryRepository;

@Service
public class HistoryService {
	
private final HistoryRepository historyRepository;
	
	public HistoryService(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}
	
	@Transactional
	public void create(Restaurant restaurant, User user) {
		if(!hasUserVisited(restaurant, user)) {
			History history = new History();
			history.setRestaurant(restaurant);
			history.setUser(user);
			historyRepository.save(history);
		}
	}
	
	public boolean hasUserVisited(Restaurant restaurant, User user) {
    	return historyRepository.findByRestaurantAndUser(restaurant, user)!= null;
    }
}