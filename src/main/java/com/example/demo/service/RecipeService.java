package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.demo.dto.recipe.RecipeProjection;
import com.example.demo.dto.recipe.RecipeResDTO;
import com.example.demo.dto.recipe.RecipeSummaryDTO;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;

public interface RecipeService {
	List<RecipeResDTO> getAllRecipes(String name, String description, List<String> ingredientNames);

	List<RecipeSummaryDTO> getAllWithSummaryUseJPQL();

	List<RecipeProjection> getAllWidthSummaryUseProjection();

	List<RecipeProjection> getAllWidthSummaryUseNativeSQL();

	Page<RecipeProjection> getSummaryPaged(int page, int size);

	RecipeResDTO getRecipeById(Long id);

	Recipe createRecipe(Recipe recipe);

	Recipe updateRecipe(Long id, Recipe recipe);

	void deleteRecipe(Long id);

	RecipeIngredient addIngredient2Recipe(Long recipeId, Long ingredientId, Double quantity);

	void deleteIngredientFromRecipe(Long recipeId, Long ingredientId);

	Recipe createRecipeWithIngredients(Recipe recipe, Map<Long, Double> ingredients);
}
