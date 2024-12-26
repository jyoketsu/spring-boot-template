package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.ingredient.IngredientDTO;
import com.example.demo.DTO.recipe.RecipeResDTO;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import com.example.demo.model.RecipeIngredientId;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	private RecipeResDTO convertToDTO(Recipe recipe) {
		RecipeResDTO dto = new RecipeResDTO();
		dto.setId(recipe.getId());
		dto.setName(recipe.getName());
		dto.setDescription(recipe.getDescription());
		dto.setContent(recipe.getContent());

		dto.setIngredients(recipe.getRecipeIngredients().stream()
				.map(this::convertIngredientToDTO)
				.collect(Collectors.toList()));
		return dto;
	}

	private IngredientDTO convertIngredientToDTO(RecipeIngredient ingredient) {
		IngredientDTO dto = new IngredientDTO();
		dto.setId(ingredient.getIngredient().getId());
		dto.setIngredientName(ingredient.getIngredient().getName());
		dto.setQuantity(ingredient.getQuantity());
		return dto;
	}

	@Override
	public List<RecipeResDTO> getAllRecipes() {
		List<Recipe> recipes = recipeRepository.findAll();
		return recipes.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public RecipeResDTO getRecipeById(Long id) {
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		return convertToDTO(recipe);
	}

	@Override
	public Recipe createRecipe(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	@Override
	public Recipe updateRecipe(Long id, Recipe recipe) {
		return recipeRepository.findById(id).map(existingRecipe -> {
			existingRecipe.setName(recipe.getName());
			existingRecipe.setDescription(recipe.getDescription());
			existingRecipe.setContent(recipe.getContent());
			return recipeRepository.save(existingRecipe);
		}).orElse(null);
	}

	@Override
	public void deleteRecipe(Long id) {
		recipeRepository.deleteById(id);
	}

	@Override
	public RecipeIngredient addIngredient2Recipe(Long recipeId, Long ingredientId, Double quantity) {
		RecipeIngredient recipeIngredient = new RecipeIngredient();
		Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
		Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
		recipeIngredient.setRecipe(recipe);
		recipeIngredient.setIngredient(ingredient);
		recipeIngredient.setQuantity(quantity);
		return recipeIngredientRepository.save(recipeIngredient);
	}

	@Override
	public void deleteIngredientFromRecipe(Long recipeId, Long ingredientId) {
		RecipeIngredientId recipeIngredientId = new RecipeIngredientId(recipeId, ingredientId);
		recipeIngredientRepository.deleteById(recipeIngredientId);
	}

	@Override
	public Recipe createRecipeWithIngredients(Recipe recipe, Map<Long, Double> ingredients) {
		// 保存recipe
		Recipe createdRecipe = createRecipe(recipe);

		// 遍历 ingredients，创建并保存 RecipeIngredient
		for (Map.Entry<Long, Double> entry : ingredients.entrySet()) {
			Long ingredientId = entry.getKey();
			Double quantity = entry.getValue();

			// 获取 Ingredient
			Ingredient ingredient = ingredientRepository.findById(ingredientId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid ingredient ID: " + ingredientId));

			// 创建 RecipeIngredient
			RecipeIngredient recipeIngredient = new RecipeIngredient();
			recipeIngredient.setRecipe(createdRecipe);
			recipeIngredient.setIngredient(ingredient);
			recipeIngredient.setQuantity(quantity);

			// 保存 RecipeIngredient
			recipeIngredientRepository.save(recipeIngredient);
		}

		return createdRecipe;
	}

}
