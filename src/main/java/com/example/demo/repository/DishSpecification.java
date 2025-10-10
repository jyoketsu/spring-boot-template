package com.example.demo.repository;

import com.example.demo.model.Dictionary;
import com.example.demo.model.Dish;

import org.springframework.data.jpa.domain.Specification;

public class DishSpecification {
	public static Specification<Dish> hasName(String name) {
		return (root, query, criteriaBuilder) -> name == null || name.isEmpty()
				? null
				: criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}

	public static Specification<Dish> hasCategory(Dictionary category) {
		return (root, query, criteriaBuilder) -> category == null
				? null
				: criteriaBuilder.equal(root.get("category"), category);
	}

	public static Specification<Dish> hasCategoryId(Long categoryId) {
		return (root, query, criteriaBuilder) -> categoryId == null
				? null
				: criteriaBuilder.equal(root.get("category").get("id"), categoryId);
	}
}
