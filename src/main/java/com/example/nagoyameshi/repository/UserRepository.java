package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> { 
	public User findByEmail(String email);
	public Page<User> findByUserNameLikeOrFuriganaLikeOrEmailLike(String nameKeyword, String furiganaKeyword, String emailKeyword, Pageable pageable);
}