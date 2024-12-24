package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dictionaries")
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
