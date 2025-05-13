package com.example.demo.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IngredientBodyDTO {
	// 修改时需要传递的 ID，新增时可以为 null
	private Long id;
	@NotBlank(message = "Ingredient name is required")
	private String name;
	@NotNull(message = "Unit is required")
	private Long unitId;
}
