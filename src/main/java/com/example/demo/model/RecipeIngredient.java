package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
	@EmbeddedId
	private RecipeIngredientId id = new RecipeIngredientId();

	@ManyToOne
	// @MapsId 是 JPA 中用于处理复合主键映射的注解。它的作用是指定从属实体中的一个字段（通常是关系字段，如
	// @ManyToOne）如何与嵌入式主键类（@EmbeddedId 或 @IdClass）的主键字段映射。
	@MapsId("recipeId") // 对应复合主键中的 recipeId
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne
	@MapsId("ingredientId") // 对应复合主键中的 ingredientId
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	@Column(name = "quantity")
	private Double quantity;

	public RecipeIngredientId getId() {
		return id;
	}

	public void setId(RecipeIngredientId id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
}
