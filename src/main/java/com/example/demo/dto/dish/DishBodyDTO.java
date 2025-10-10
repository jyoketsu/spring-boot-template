package com.example.demo.dto.dish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishBodyDTO {
	private Long id;
	@NotBlank(message = "Dish name is required")
	private String name;
	@NotBlank(message = "Dish description is required")
	private String description;
	@NotNull(message = "Category is required")
	private Long categoryId;
	private String cover;
}
