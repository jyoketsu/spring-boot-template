package com.example.demo.dto.recipe;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RecipeListDTO {
	private Long id;
	private String name;
	private String description;
	private String ingredients;
	private Long dishId;
	private String dishName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;
}
