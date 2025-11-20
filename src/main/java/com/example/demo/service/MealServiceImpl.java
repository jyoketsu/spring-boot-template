package com.example.demo.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.dto.dish.DishPlanDTO;
import com.example.demo.dto.meal.MealPlanDTO;
import com.example.demo.model.Dish;
import com.example.demo.repository.DishRepository;

@Service
public class MealServiceImpl implements MealService {

	@Autowired
	private DishRepository dishRepository;

	// 主食类别
	private static final String STAPLE_CODE = "staple";

	// 默认炒菜规则
	private Map<String, Integer> getDefaultRule() {
		return Map.of("big_meat", 1, "small_meat", 1, "vegetable", 1, "soup", 1);
	}

	// 生成一顿饭（炒菜模式）
	private List<DishPlanDTO> generateStirFryMeal(Map<String, Integer> rule) {
		List<DishPlanDTO> results = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : rule.entrySet()) {
			String categoryCode = entry.getKey();
			Integer count = entry.getValue();
			List<Dish> randomDishes = dishRepository.findRandomByCategoryCode(categoryCode, PageRequest.of(0, count));
			results.addAll(randomDishes.stream().map(this::convertToDishPlanDTO).collect(Collectors.toList()));
		}
		return results;
	}

	// 生成主食模式
	private List<DishPlanDTO> generateStapleMeal() {
		List<Dish> dishes = dishRepository.findRandomByCategoryCode(STAPLE_CODE, PageRequest.of(0, 1));
		return dishes.stream().map(this::convertToDishPlanDTO).collect(Collectors.toList());
	}

	private DishPlanDTO convertToDishPlanDTO(Dish dish) {
		return new DishPlanDTO()
				.setId(dish.getId())
				.setName(dish.getName())
				.setCover(dish.getCover())
				.setDescription(dish.getDescription())
				.setCategoryId(dish.getCategory().getId())
				.setCategoryName(dish.getCategory().getName())
				.setRecipeIds(dish.getRecipes().stream().map(recipe -> recipe.getId()).collect(Collectors.toList()));
	}

	// 生成一顿饭（随机模式）
	private List<DishPlanDTO> generateRandomMeal() {
		// 80% 炒菜, 20% 主食
		boolean isStaple = new Random().nextDouble() < 0.2;

		if (isStaple) {
			return generateStapleMeal();
		} else {
			return generateStirFryMeal(getDefaultRule());
		}
	}

	@Override
	public List<MealPlanDTO> generateMealPlan(int days, boolean includeLunch, boolean includeDinner) {
		List<MealPlanDTO> plan = new ArrayList<>();

		for (int day = 1; day <= days; day++) {
			MealPlanDTO dayPlan = new MealPlanDTO();
			dayPlan.setDate(LocalDate.now().plusDays(day - 1));

			if (includeLunch) {
				dayPlan.setLunch(generateRandomMeal());
			}
			if (includeDinner) {
				dayPlan.setDinner(generateRandomMeal());
			}

			plan.add(dayPlan);
		}

		return plan;
	}
}
