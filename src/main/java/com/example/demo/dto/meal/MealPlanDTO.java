package com.example.demo.dto.meal;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.Dish;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MealPlanDTO {
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
	private LocalDate date;
	private List<Dish> lunch;
	private List<Dish> dinner;
}
