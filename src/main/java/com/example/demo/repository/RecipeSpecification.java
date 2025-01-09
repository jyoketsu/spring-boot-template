package com.example.demo.repository;

import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

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

			// 使用 IN 查询匹配多个食材名（精确查询）
			// return ingredientJoin.get("name").in(ingredientNames);

			/**
			 * 模糊查询
			 * Predicate：每个 Predicate 表示一个单独的查询条件，比如 ingredient.name LIKE '%sugar%'。
			 * 流式创建 Predicate 列表
			 * 使用 Stream 遍历 ingredientNames。
			 * 对于每个食材名（name），通过 criteriaBuilder.like 生成对应的 Predicate，并将其添加到 predicates 列表中。
			 */
			List<Predicate> predicates = ingredientNames.stream()
					.map(name -> criteriaBuilder.like(ingredientJoin.get("name"), "%" + name + "%"))
					.toList();

			/**
			 * criteriaBuilder.or 将所有条件用 OR 组合。
			 * 如果有多个 Predicate，则最终生成类似于：
			 * WHERE ingredient.name LIKE '%盐%' OR ingredient.name LIKE '%青菜%'
			 */
			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		};
	}
}
