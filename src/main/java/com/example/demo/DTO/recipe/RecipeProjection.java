package com.example.demo.dto.recipe;

import java.time.LocalDateTime;

public interface RecipeProjection {
	String getName();

	LocalDateTime getUpdateTime();
}
