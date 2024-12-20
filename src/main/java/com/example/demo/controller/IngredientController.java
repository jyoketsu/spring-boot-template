package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ingredient;
import com.example.demo.service.IngredientService;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<List<Ingredient>> getAllIngredients() {
		List<Ingredient> ingredients = ingredientService.getAllIngredients();
		return ResponseEntity.ok(ingredients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
		Ingredient ingredient = ingredientService.getIngredientById(id);
		if (ingredient != null) {
			return ResponseEntity.ok(ingredient);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search")
	public ResponseEntity<List<Ingredient>> getIngredientByName(@RequestParam String name) {
		List<Ingredient> ingredients = ingredientService.getIngredientByName(name);
		return ResponseEntity.ok(ingredients);
	}

	@PostMapping
	public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
		Ingredient newIngredient = ingredientService.createIngredient(ingredient);
		return ResponseEntity.ok(newIngredient);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
		ingredientService.deleteIngredient(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
		Ingredient updatedIngredient = ingredientService.updateIngredient(id, ingredient);
		if (updatedIngredient != null) {
			return ResponseEntity.ok(updatedIngredient);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
