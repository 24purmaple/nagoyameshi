package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {

}