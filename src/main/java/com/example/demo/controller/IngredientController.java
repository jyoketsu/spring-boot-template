package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ingredient.IngredientBodyDTO;
import com.example.demo.dto.ingredient.IngredientSummaryDTO;
import com.example.demo.model.Ingredient;
import com.example.demo.service.IngredientService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

	private final IngredientService ingredientService;

	// 在Spring 4.3及之后的版本中，如果一个类只有一个构造函数，Spring 会自动将其作为依赖注入的目标，即使没有标注 @Autowired注解。
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	@GetMapping("/all")
	public List<IngredientSummaryDTO> getAll() {
		return ingredientService.getAll();
	}

	@GetMapping()
	public Page<IngredientSummaryDTO> getIngredientPaged(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Long unitId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ingredientService.getIngredientPaged(name, unitId, page, size);
	}

	@GetMapping("/{id}")
	public IngredientSummaryDTO getIngredientById(@PathVariable Long id) {
		return ingredientService.getIngredientById(id);
	}

	@GetMapping("/name")
	public List<Ingredient> getIngredientByName(@RequestParam String name) {
		return ingredientService.getIngredientByName(name);
	}

	@PostMapping
	public Ingredient addIngredient(@RequestBody @Valid IngredientBodyDTO ingredientDTO) {
		return ingredientService.createIngredient(ingredientDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteIngredient(@PathVariable Long id) {
		ingredientService.deleteIngredient(id);
	}

	@DeleteMapping
	public void deleteIngredients(@RequestBody List<Long> ids) {
		ingredientService.deleteIngredients(ids);
	}

	@PutMapping
	public Ingredient updateIngredient(
			@RequestBody @Valid IngredientBodyDTO ingredientDTO) {
		return ingredientService.updateIngredient(ingredientDTO);
	}
}
