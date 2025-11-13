package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MealRule;

public interface MealRepository extends JpaRepository<MealRule, Long> {

}
