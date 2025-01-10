package com.example.demo.service;

import java.util.List;
import org.springframework.data.domain.Page;

import com.example.demo.dto.recipe.RecipeDTO;
import com.example.demo.dto.recipe.RecipeListDTO;
import com.example.demo.dto.recipe.RecipeProjection;
import com.example.demo.dto.recipe.RecipeResDTO;
import com.example.demo.dto.recipe.RecipeSummaryDTO;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;

public interface RecipeService {
	Page<RecipeListDTO> getAllRecipes(String name, String description, String ingredientNames, int page, int size);

	List<RecipeSummaryDTO> getAllWithSummaryUseJPQL();

	List<RecipeProjection> getAllWidthSummaryUseProjection();

	List<RecipeProjection> getAllWidthSummaryUseNativeSQL();

	Page<RecipeProjection> getSummaryPaged(int page, int size);

	RecipeResDTO getRecipeById(Long id);

	void deleteRecipe(Long id);

	RecipeIngredient addIngredient2Recipe(Long recipeId, Long ingredientId, Double quantity);

	void deleteIngredientFromRecipe(Long recipeId, Long ingredientId);

	Recipe createRecipeWithIngredients(RecipeDTO recipeDto);

	Recipe editRecipeWithIngredients(RecipeDTO recipeDTO);
}
