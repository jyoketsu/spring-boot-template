package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.ingredient.IngredientBodyDTO;
import com.example.demo.dto.ingredient.IngredientSummaryDTO;
import com.example.demo.model.Ingredient;

public interface IngredientService {
	List<IngredientSummaryDTO> getAll();

	Page<IngredientSummaryDTO> getIngredientPaged(String name, Long unitId, int page, int size);

	IngredientSummaryDTO getIngredientById(Long id);

	List<Ingredient> getIngredientByName(String name);

	Ingredient createIngredient(IngredientBodyDTO ingredientDTO);

	Ingredient updateIngredient(IngredientBodyDTO ingredientDTO);

	void deleteIngredient(Long id);

}
