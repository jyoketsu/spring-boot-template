package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.dish.DishBodyDTO;
import com.example.demo.dto.dish.DishListDTO;
import com.example.demo.model.Dish;
import com.example.demo.service.DishService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dish")
public class DishController {

	@Autowired
	private DishService dishService;

	@GetMapping
	public Page<DishListDTO> getAllRecipes(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return dishService.getAllDishes(name, categoryId, page, size);
	}

	@GetMapping("/{id}")
	public DishListDTO getIngredientById(@PathVariable Long id) {
		return dishService.getDishById(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Dish addDish(@RequestBody @Valid DishBodyDTO dishBodyDTO) {
		return dishService.createDish(dishBodyDTO);
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Dish updateDish(
			@RequestBody @Valid DishBodyDTO dishBodyDTO) {
		return dishService.updateDish(dishBodyDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteDish(@PathVariable Long id) {
		dishService.deleteDish(id);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteDishes(@RequestBody List<Long> ids) {
		dishService.deleteDishes(ids);
	}

}
