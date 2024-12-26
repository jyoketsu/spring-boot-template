package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.recipe.AddIngredientDTO;
import com.example.demo.DTO.recipe.RecipeDTO;
import com.example.demo.DTO.recipe.RecipeResDTO;
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
	public ResponseEntity<List<RecipeResDTO>> getAllRecipes() {
		List<RecipeResDTO> recipes = recipeService.getAllRecipes();
		return ResponseEntity.ok(recipes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecipeResDTO> getRecipeById(@PathVariable Long id) {
		RecipeResDTO recipe = recipeService.getRecipeById(id);
		if (recipe != null) {
			return ResponseEntity.ok(recipe);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping()
	public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
		Recipe newRecipe = recipeService.createRecipe(recipe);
		return ResponseEntity.ok(newRecipe);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
		Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
		if (updatedRecipe != null) {
			return ResponseEntity.ok(updatedRecipe);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
		recipeService.deleteRecipe(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/addIngredient")
	public ResponseEntity<RecipeIngredient> addIngredient2Recipe(@RequestBody AddIngredientDTO addIngredientDto) {
		RecipeIngredient recipeIngredient = recipeService.addIngredient2Recipe(
				addIngredientDto.getRecipeId(),
				addIngredientDto.getIngredientId(),
				addIngredientDto.getQuantity());
		return ResponseEntity.ok(recipeIngredient);
	}

	@DeleteMapping("/removeIngredient/{recipeId}/{ingredientId}")
	public ResponseEntity<Void> removeIngredientFromRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
		recipeService.deleteIngredientFromRecipe(recipeId, ingredientId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/createWithIngredients")
	public ResponseEntity<Recipe> addRecipeWithIngredients(@RequestBody RecipeDTO recipeDto) {
		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.getName());
		recipe.setDescription(recipeDto.getDescription());
		recipe.setContent(recipeDto.getContent());
		Recipe createdRecipe = recipeService.createRecipeWithIngredients(recipe, recipeDto.getIngredients());
		return ResponseEntity.ok(createdRecipe);
	}

}
