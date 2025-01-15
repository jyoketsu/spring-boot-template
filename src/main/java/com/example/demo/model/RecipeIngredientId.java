package com.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
// generate constructors :
// @NoArgsConstructor@RequiredArgsConstructor@AllArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecipeIngredientId implements Serializable {
	private Long recipeId;
	private Long ingredientId;
}