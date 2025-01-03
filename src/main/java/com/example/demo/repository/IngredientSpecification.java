package com.example.demo.repository;

import com.example.demo.model.Dictionary;
import com.example.demo.model.Ingredient;
import org.springframework.data.jpa.domain.Specification;

public class IngredientSpecification {

	// 按名称模糊查询
	public static Specification<Ingredient> hasName(String name) {
		return (root, query, criteriaBuilder) -> name == null || name.isEmpty()
				? null
				: criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}

	// 按单位精确查询
	public static Specification<Ingredient> hasUnit(Dictionary unit) {
		return (root, query, criteriaBuilder) -> unit == null
				? null
				: criteriaBuilder.equal(root.get("unit"), unit);
	}

	// 按单位精确查询
	public static Specification<Ingredient> hasUnitId(Long unitId) {
		return (root, query, criteriaBuilder) -> unitId == null
				? null
				: criteriaBuilder.equal(root.get("unit").get("id"), unitId);
	}

	// 如果以后需要扩展查询条件，可以直接在 IngredientSpecification 中添加新的方法即可。
}
