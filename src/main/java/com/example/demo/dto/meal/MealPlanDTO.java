package com.example.demo.dto.meal;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.dish.DishPlanDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MealPlanDTO {
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
	private LocalDate date;
	private List<DishPlanDTO> lunch;
	private List<DishPlanDTO> dinner;
}
