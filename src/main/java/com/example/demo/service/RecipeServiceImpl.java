package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ingredient.IngredientDTO;
import com.example.demo.dto.recipe.RecipeListDTO;
import com.example.demo.dto.recipe.RecipeProjection;
import com.example.demo.dto.recipe.RecipeResDTO;
import com.example.demo.dto.recipe.RecipeSummaryDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import com.example.demo.model.RecipeIngredientId;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.RecipeSpecification;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Override
	public Page<RecipeListDTO> getAllRecipes(String name, String description, String ingredientNames, int page,
			int size) {
		// 通过 Specification 构造查询条件
		Specification<Recipe> spec = Specification
				.where(RecipeSpecification.hasName(name))
				.and(RecipeSpecification.hasDescription(description))
				.and(RecipeSpecification.hasIngredients(
						ingredientNames != null ? Arrays.asList(ingredientNames.split("\\s+")) : null));

		// 分页请求
		PageRequest pageable = PageRequest.of(page, size);

		// 执行查询
		Page<Recipe> recipes = recipeRepository.findAll(spec, pageable);

		// 将查询结果转换为 DTO
		return recipes
				.map(this::convert2ListDTO);
	}

	@Override
	public List<RecipeSummaryDTO> getAllWithSummaryUseJPQL() {
		return recipeRepository.findAllWithSummaryUseJPQL();
	}

	// @Override
	public List<RecipeProjection> getAllWidthSummaryUseProjection() {
		return recipeRepository.findAllBy();
	}

	@Override
	public List<RecipeProjection> getAllWidthSummaryUseNativeSQL() {
		return recipeRepository.findAllWidthSummaryUseNativeSQL();
	}

	@Override
	public Page<RecipeProjection> getSummaryPaged(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return recipeRepository.findSummaryPaged(pageable);
	}

	@Override
	public RecipeResDTO getRecipeById(Long id) {
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("recipe not found with id " + id));
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
		}).orElseThrow(() -> new ResourceNotFoundException("recipe not found with id " + id));
	}

	@Override
	public void deleteRecipe(Long id) {
		recipeRepository.deleteById(id);
	}

	@Override
	public RecipeIngredient addIngredient2Recipe(Long recipeId, Long ingredientId, Double quantity) {
		RecipeIngredient recipeIngredient = new RecipeIngredient();

		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException("recipe not found with id " + recipeId));

		Ingredient ingredient = ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new ResourceNotFoundException("ingredient not found with id " + ingredientId));

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
					.orElseThrow(() -> new ResourceNotFoundException("ingredient not found with id " + ingredientId));

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

	private RecipeListDTO convert2ListDTO(Recipe recipe) {
		RecipeListDTO dto = new RecipeListDTO();
		dto.setId(recipe.getId());
		dto.setName(recipe.getName());
		dto.setDescription(recipe.getDescription());
		dto.setUpdateTime(recipe.getUpdateTime());
		dto.setIngredients(recipe.getRecipeIngredients().stream()
				.map(ri -> ri.getIngredient().getName())
				.collect(Collectors.joining(",")));
		return dto;
	}

	private RecipeResDTO convertToDTO(Recipe recipe) {
		RecipeResDTO dto = new RecipeResDTO();
		dto.setId(recipe.getId());
		dto.setName(recipe.getName());
		dto.setDescription(recipe.getDescription());
		dto.setContent(recipe.getContent());

		/**
		 * 从 recipe 中获取 List<RecipeIngredient>。
		 * 
		 * .stream() 将 List<RecipeIngredient> 转换为 Stream<RecipeIngredient>。
		 * 
		 * 使用 map 方法，将每个 RecipeIngredient 转换为对应的 RecipeIngredientDTO。
		 * 
		 * 将流中的元素收集为一个 List：使用 collect 将流结果重新组装为一个列表。
		 */
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
		dto.setUnit(ingredient.getIngredient().getUnit().getName());
		return dto;
	}

}
