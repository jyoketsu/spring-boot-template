package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class RecipeIngredientId implements Serializable {
	private Long recipeId;
	private Long ingredientId;

	// 默认构造函数
	public RecipeIngredientId() {
	}

	// 参数化构造函数
	public RecipeIngredientId(Long recipeId, Long ingredientId) {
		this.recipeId = recipeId;
		this.ingredientId = ingredientId;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}
}