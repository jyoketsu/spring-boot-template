package com.example.demo.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IngredientBodyDTO {
	// 修改时需要传递的 ID，新增时可以为 null
	private Long id;
	@NotBlank(message = "Ingredient name is required")
	private String name;
	@NotNull(message = "Unit is required")
	private Long unitId;

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

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
}
