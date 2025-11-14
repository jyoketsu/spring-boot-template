package com.example.demo.service;

import java.util.*;

import com.example.demo.dto.meal.MealPlanDTO;

public interface MealService {
	List<MealPlanDTO> generateMealPlan(int days, boolean includeLunch, boolean includeDinner);
}
