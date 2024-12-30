package com.example.demo.projection;

import java.time.LocalDateTime;

public interface RecipeProjection {
	String getName();

	LocalDateTime getUpdateTime();
}
