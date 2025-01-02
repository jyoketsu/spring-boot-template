package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ingredient;
import com.example.demo.service.IngredientService;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

	private final IngredientService ingredientService;

	// 在Spring 4.3及之后的版本中，如果一个类只有一个构造函数，Spring 会自动将其作为依赖注入的目标，即使没有标注 @Autowired注解。
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	@GetMapping
	public List<Ingredient> getAllIngredients() {
		return ingredientService.getAllIngredients();
	}

	@GetMapping("/{id}")
	public Ingredient getIngredientById(@PathVariable Long id) {
		return ingredientService.getIngredientById(id);
	}

	@GetMapping("/search/{name}")
	public List<Ingredient> getIngredientByName(@PathVariable String name) {
		return ingredientService.getIngredientByName(name);
	}

	@PostMapping
	public Ingredient addIngredient(@RequestParam String name, @RequestParam Long unitId) {
		return ingredientService.createIngredient(name, unitId);
	}

	@DeleteMapping("/{id}")
	public void deleteIngredient(@PathVariable Long id) {
		ingredientService.deleteIngredient(id);
	}

	@PutMapping("/{id}")
	public Ingredient updateIngredient(
			@PathVariable Long id,
			@RequestParam String name,
			@RequestParam Long unitId) {
		return ingredientService.updateIngredient(id, name, unitId);
	}
}
