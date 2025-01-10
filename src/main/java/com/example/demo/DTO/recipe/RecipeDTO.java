package com.example.demo.dto.recipe;

import java.util.Map;

public class RecipeDTO {
	private Long id;
	private String name;
	private String description;
	private String content;
	// Map: Key 为 Ingredient ID, Value 为数量
	private Map<Long, Double> ingredients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<Long, Double> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<Long, Double> ingredients) {
		this.ingredients = ingredients;
	}

}
