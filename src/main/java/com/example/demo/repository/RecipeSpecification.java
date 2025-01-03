package com.example.demo.repository;

import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RecipeSpecification {

	// 按菜谱名模糊查询
	public static Specification<Recipe> hasName(String name) {
		return (root, query, criteriaBuilder) -> name == null ? null
				: criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}

	// 按描述模糊查询
	public static Specification<Recipe> hasDescription(String description) {
		return (root, query, criteriaBuilder) -> description == null ? null
				: criteriaBuilder.like(root.get("description"), "%" + description + "%");
	}

	// 按食材名查询（支持多个）
	public static Specification<Recipe> hasIngredients(List<String> ingredientNames) {
		return (root, query, criteriaBuilder) -> {
			if (ingredientNames == null || ingredientNames.isEmpty()) {
				return null;
			}
			// Join 到 RecipeIngredient 表
			Join<Recipe, RecipeIngredient> recipeIngredientJoin = root.join("recipeIngredients");
			// Join 到 Ingredient 表
			Join<RecipeIngredient, String> ingredientJoin = recipeIngredientJoin.join("ingredient");

			// 使用 IN 查询匹配多个食材名
			return ingredientJoin.get("name").in(ingredientNames);
		};
	}
}
