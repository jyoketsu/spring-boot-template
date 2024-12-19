package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

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

	private final IngredientRepository ingredientRepository;

	public IngredientController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@GetMapping
	public ResponseEntity<List<Ingredient>> getAllIngredients() {
		List<Ingredient> ingredients = ingredientRepository.findAll();
		return ResponseEntity.ok(ingredients);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {

		// Java 8 的 Optional 和 Lambda 表达式
		return ingredientRepository.findById(id)
				// 如果 Optional 中有值（即找到了对应的 Ingredient），map
				// 会将值（Ingredient）传递给方法ResponseEntity.ok()
				.map(ResponseEntity::ok)
				// 如果 Optional 是空的（即没有找到对应的 Ingredient），执行 orElse 中的逻辑
				.orElse(ResponseEntity.notFound().build());

		// 传统写法（不使用 Optional）
		// Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		// if (optionalIngredient.isPresent()) {
		// return ResponseEntity.ok(optionalIngredient.get());
		// } else {
		// return ResponseEntity.notFound().build();
		// }
	}


	@GetMapping("/search")
	public ResponseEntity<List<Ingredient>> getIngredientByName(@RequestParam String name) {
		List<Ingredient> ingredients = ingredientRepository.findByName(name);
		return ResponseEntity.ok(ingredients);
	}

	@PostMapping
	public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
		Ingredient savedIngredient = ingredientRepository.save(ingredient);
		return ResponseEntity.ok(savedIngredient);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		if (optionalIngredient.isPresent()) {
			ingredientRepository.delete(optionalIngredient.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
		return ingredientRepository.findById(id)
				.map(existingIngredient -> {
					existingIngredient.setName(ingredient.getName());
					existingIngredient.setUnit(ingredient.getUnit());
					Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);
					return ResponseEntity.ok(updatedIngredient);
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
