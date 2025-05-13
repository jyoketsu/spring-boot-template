package com.example.demo.dto.ingredient;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IngredientDTO {
	private Long id;
	private String name;
	private Double quantity;
	private Long unitId;
	private String unit;
}
