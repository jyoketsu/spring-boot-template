package com.example.demo.repository;

import com.example.demo.model.Dictionary;
import org.springframework.data.jpa.domain.Specification;

public class DictionarySpecification {

	// 按名称模糊查询
	public static Specification<Dictionary> hasName(String name) {
		return (root, query, criteriaBuilder) -> name == null || name.isEmpty()
				? null
				: criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}

	// 按字典类型精确查询
	public static Specification<Dictionary> hasDictType(String dictType) {
		return (root, query, criteriaBuilder) -> dictType == null
				? null
				: criteriaBuilder.equal(root.get("dictType"), dictType);
	}
}
