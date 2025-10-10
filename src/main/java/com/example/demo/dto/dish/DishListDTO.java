package com.example.demo.dto.dish;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DishListDTO {
	private Long id;
	private String name;
	private String description;
	private String cover;
	private Long categoryId;
	private String categoryName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;
}
