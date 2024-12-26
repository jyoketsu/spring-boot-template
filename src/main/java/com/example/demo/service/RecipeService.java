package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.DTO.recipe.RecipeResDTO;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;

public interface RecipeService {
	List<RecipeResDTO> getAllRecipes();

	RecipeResDTO getRecipeById(Long id);

	Recipe createRecipe(Recipe recipe);

	Recipe updateRecipe(Long id, Recipe recipe);

	void deleteRecipe(Long id);

	RecipeIngredient addIngredient2Recipe(Long recipeId, Long ingredientId, Double quantity);

	void deleteIngredientFromRecipe(Long recipeId, Long ingredientId);

	Recipe createRecipeWithIngredients(Recipe recipe, Map<Long, Double> ingredients);
}
