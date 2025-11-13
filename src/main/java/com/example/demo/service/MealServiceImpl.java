package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Dish;
import com.example.demo.repository.DishRepository;

@Service
public class MealServiceImpl implements MealService {

	@Autowired
	private DishRepository dishRepository;

	private Map<String, Integer> getDefaultRule() {
		return Map.of("big_meat", 1, "small_meat", 1, "vegetable", 1, "soup", 1);
	}

	@Override
	public List<Dish> generateMeal() {
		Map<String, Integer> rule = getDefaultRule();
		List<Dish> results = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : rule.entrySet()) {
			String categoryCode = entry.getKey();
			Integer count = entry.getValue();
			List<Dish> randomDishes = dishRepository.findRandomByCategoryCode(categoryCode, count);
			results.addAll(randomDishes);
		}
		return results;
	}
}
