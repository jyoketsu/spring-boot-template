package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Ingredient;

public interface IngredientService {
	List<Ingredient> getAllIngredients();

	Ingredient getIngredientById(Long id);

	List<Ingredient> getIngredientByName(String name);

	Ingredient createIngredient(Ingredient ingredient);

	Ingredient updateIngredient(Long id, Ingredient ingredient);

	void deleteIngredient(Long id);

}
