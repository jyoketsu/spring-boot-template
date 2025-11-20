package com.example.demo.dto.dish;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishPlanDTO {
	private Long id;
	private String name;
	private String description;
	private String cover;
	private Long categoryId;
	private String categoryName;
	private List<Long> recipeIds;
}
