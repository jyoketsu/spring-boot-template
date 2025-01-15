package com.example.demo.dto.recipe;

import java.util.List;

import com.example.demo.dto.ingredient.IngredientDTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RecipeResDTO {
	private Long id;
	private String name;
	private String description;
	private String content;

	private List<IngredientDTO> ingredients;
}
