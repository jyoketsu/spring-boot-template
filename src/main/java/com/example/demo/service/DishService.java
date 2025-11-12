package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.dish.DishBodyDTO;
import com.example.demo.dto.dish.DishListDTO;
import com.example.demo.model.Dish;

public interface DishService {
	Page<DishListDTO> getAllDishes(Long id, String name, Long categoryId, Integer page, Integer size);

	DishListDTO getDishById(Long id);

	Dish createDish(DishBodyDTO dishBodyDTO);

	Dish updateDish(DishBodyDTO dishBodyDTO);

	void deleteDish(Long id);

	void deleteDishes(List<Long> ids);
}
