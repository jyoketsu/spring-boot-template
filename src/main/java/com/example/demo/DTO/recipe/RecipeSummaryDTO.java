package com.example.demo.dto.recipe;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RecipeSummaryDTO {
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
