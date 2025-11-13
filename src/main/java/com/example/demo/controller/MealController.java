package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dish;
import com.example.demo.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {
	@Autowired
	private MealService mealService;

	@GetMapping("/random")
	public List<Dish> generateMeal() {
		return mealService.generateMeal();
	}
}
