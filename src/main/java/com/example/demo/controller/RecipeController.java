package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.recipe.AddIngredientDTO;
import com.example.demo.dto.recipe.RecipeDTO;
import com.example.demo.dto.recipe.RecipeListDTO;
import com.example.demo.dto.recipe.RecipeProjection;
import com.example.demo.dto.recipe.RecipeResDTO;
import com.example.demo.dto.recipe.RecipeSummaryDTO;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import com.example.demo.service.RecipeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping
	public Page<RecipeListDTO> getAllRecipes(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String ingredientNames,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return recipeService.getAllRecipes(name, description, ingredientNames, page, size);
	}

	@GetMapping("/list/JPQL")
	public List<RecipeSummaryDTO> getAllWithSummaryUseJPQL() {
		return recipeService.getAllWithSummaryUseJPQL();
	}

	@GetMapping("/list/projection")
	public List<RecipeProjection> getAllWithSummaryUseProjection() {
		return recipeService.getAllWidthSummaryUseProjection();
	}

	@GetMapping("/list/native")
	public List<RecipeProjection> getAllWithSummaryUseNativeSQL() {
		return recipeService.getAllWidthSummaryUseNativeSQL();
	}

	@GetMapping("/list/page")
	public Page<RecipeProjection> getSummaryPaged(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return recipeService.getSummaryPaged(page, size);
	}

	@GetMapping("/{id}")
	public RecipeResDTO getRecipeById(@PathVariable Long id) {
		return recipeService.getRecipeById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteRecipe(@PathVariable Long id) {
		recipeService.deleteRecipe(id);
	}

	@DeleteMapping
	public void deleteRecipes(@RequestBody List<Long> ids) {
		recipeService.deleteRecipes(ids);
	}

	@PostMapping("/addIngredient")
	public RecipeIngredient addIngredient2Recipe(@RequestBody AddIngredientDTO addIngredientDto) {
		return recipeService.addIngredient2Recipe(
				addIngredientDto.getRecipeId(),
				addIngredientDto.getIngredientId(),
				addIngredientDto.getQuantity());
	}

	@DeleteMapping("/removeIngredient/{recipeId}/{ingredientId}")
	public void removeIngredientFromRecipe(@PathVariable Long recipeId,
			@PathVariable Long ingredientId) {
		recipeService.deleteIngredientFromRecipe(recipeId, ingredientId);
	}

	@PostMapping
	public Recipe addRecipeWithIngredients(@RequestBody RecipeDTO recipeDto) {
		return recipeService.createRecipeWithIngredients(recipeDto);
	}

	@PutMapping
	public Recipe editRecipeWithIngredients(@RequestBody RecipeDTO recipeDto) {
		return recipeService.editRecipeWithIngredients(recipeDto);
	}
}
