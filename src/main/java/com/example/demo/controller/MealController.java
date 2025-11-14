package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.meal.MealPlanDTO;
import com.example.demo.service.MealService;

@RestController
@RequestMapping("/api/meals")
public class MealController {
	@Autowired
	private MealService mealService;

	@GetMapping("/random")
	public List<MealPlanDTO> generateMeal(@RequestParam int days, @RequestParam boolean includeLunch,
			@RequestParam boolean includeDinner) {
		return mealService.generateMealPlan(days, includeLunch, includeDinner);
	}
}
