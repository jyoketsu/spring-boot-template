package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Dictionary;
import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;

// @Service 是 Spring 框架中的一个注解，用于将类标识为服务层的组件，并将其注册为 Spring 容器中的一个 Bean。
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;

	public IngredientServiceImpl(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public List<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}

	@Override
	public Ingredient getIngredientById(Long id) {
		return ingredientRepository.findById(id).orElse(null);

		// 传统写法（不使用 Optional）
		// Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
		// if (optionalIngredient.isPresent()) {
		// return optionalIngredient.get();
		// } else {
		// return null;
		// }
	}

	@Override
	public List<Ingredient> getIngredientByName(String name) {
		return ingredientRepository.findByName(name);
	}

	@Override
	public Ingredient createIngredient(String name, Long unitId) {
		Ingredient ingredient = new Ingredient();
		ingredient.setName(name);
		Dictionary unit = new Dictionary();
		unit.setId(unitId);
		ingredient.setUnit(unit);
		return ingredientRepository.save(ingredient);
	}

	@Override
	public Ingredient updateIngredient(Long id, String name, Long unitId) {
		Dictionary unit = new Dictionary();
		unit.setId(unitId);
		return ingredientRepository.findById(id)
				.map(existingIngredient -> {
					existingIngredient.setName(name);
					existingIngredient.setUnit(unit);
					Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);
					return updatedIngredient;
				})
				.orElseThrow(() -> new ResourceNotFoundException("ingredient not found with id " + id));
	}

	@Override
	public void deleteIngredient(Long id) {
		ingredientRepository.deleteById(id);
	}

}
