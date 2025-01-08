package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.recipe.AddIngredientDTO;
import com.example.demo.dto.recipe.RecipeDTO;
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

	@GetMapping("/list/search")
	public List<RecipeResDTO> getAllRecipes(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) List<String> ingredientNames) {
		return recipeService.getAllRecipes(name, description, ingredientNames);
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

	@PostMapping()
	public Recipe addRecipe(@RequestBody Recipe recipe) {
		return recipeService.createRecipe(recipe);
	}

	@PutMapping("/{id}")
	public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
		return recipeService.updateRecipe(id, recipe);
	}

	@DeleteMapping("/{id}")
	public void deleteRecipe(@PathVariable Long id) {
		recipeService.deleteRecipe(id);
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

	@PostMapping("/createWithIngredients")
	public Recipe addRecipeWithIngredients(@RequestBody RecipeDTO recipeDto) {
		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.getName());
		recipe.setDescription(recipeDto.getDescription());
		recipe.setContent(recipeDto.getContent());
		return recipeService.createRecipeWithIngredients(recipe, recipeDto.getIngredients());
	}
}
