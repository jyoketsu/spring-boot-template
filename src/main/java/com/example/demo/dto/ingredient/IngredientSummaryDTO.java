package com.example.demo.dto.ingredient;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IngredientSummaryDTO {
    private Long id;
    private String name;
    private String unitId;
    private String unitName;
}
