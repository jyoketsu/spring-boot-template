package com.example.demo.dto.recipe;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson 遇到不认识的字段就会自动跳过，不再报错
public class RecipeDTO {
	private Long id;
	private String name;
	private String description;
	private String content;
	// Map: Key 为 Ingredient ID, Value 为数量
	private Map<Long, Double> ingredients;
	private Long dishId;
}
