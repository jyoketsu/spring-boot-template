package com.example.demo.dto.recipe;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface RecipeProjection {
	String getName();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime getUpdateTime();
}
