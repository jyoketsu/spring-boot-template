package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		// 表名
		name = "dictionaries",
		// 唯一约束
		uniqueConstraints = {
				// 联合唯一约束 : dict_type 和 dict_code 的组合必须唯一
				@UniqueConstraint(columnNames = { "dict_type", "dict_code" }),
				@UniqueConstraint(columnNames = { "dict_type", "name" })
		})
public class Dictionary extends NamedEntity {
	@Column(name = "dict_type", nullable = false)
	// 字典类型（例如 "unit"、"category" 等）
	private String dictType;

	@Column(name = "dict_code", nullable = false)
	// 字典键（例如 "g", "litre" 等）
	private String dictCode;

	public String getDictType() {
		// 字典类型（例如 "unit"、"category" 等）
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
}
