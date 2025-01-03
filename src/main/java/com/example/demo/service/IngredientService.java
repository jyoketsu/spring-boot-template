package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Ingredient;

public interface IngredientService {
	List<Ingredient> getAllIngredients(String name, Long unitId);

	Ingredient getIngredientById(Long id);

	List<Ingredient> getIngredientByName(String name);

	Ingredient createIngredient(String name, Long unitId);

	Ingredient updateIngredient(Long id, String name, Long unitId);

	void deleteIngredient(Long id);

}
