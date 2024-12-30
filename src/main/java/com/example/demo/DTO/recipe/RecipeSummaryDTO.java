package com.example.demo.DTO.recipe;

import java.time.LocalDateTime;

public class RecipeSummaryDTO {
	private String name;
	private LocalDateTime updateTime;

	public RecipeSummaryDTO(String name, LocalDateTime updateTime) {
		this.name = name;
		this.updateTime = updateTime;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
}
