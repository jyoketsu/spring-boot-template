package com.example.demo.dto.recipe;

import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RecipeDTO {
	private Long id;
	private String name;
	private String description;
	private String content;
	// Map: Key 为 Ingredient ID, Value 为数量
	private Map<Long, Double> ingredients;
}
