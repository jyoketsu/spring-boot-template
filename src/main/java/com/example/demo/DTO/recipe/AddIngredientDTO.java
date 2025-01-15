package com.example.demo.dto.recipe;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddIngredientDTO {
	private Long recipeId;
	private Long ingredientId;
	private Double quantity;
}
