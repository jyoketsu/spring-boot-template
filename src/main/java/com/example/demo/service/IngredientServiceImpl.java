package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ingredient.IngredientBodyDTO;
import com.example.demo.dto.ingredient.IngredientSummaryDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Dictionary;
import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.IngredientSpecification;

// @Service 是 Spring 框架中的一个注解，用于将类标识为服务层的组件，并将其注册为 Spring 容器中的一个 Bean。
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;

	public IngredientServiceImpl(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public List<IngredientSummaryDTO> getAll() {
		List<Ingredient> ingredients = ingredientRepository.findAll();
		return ingredients.stream().map(this::convertToSummaryDTO).toList();
	}

	@Override
	public Page<IngredientSummaryDTO> getIngredientPaged(Long id, String name, Long unitId, int page, int size) {
		// 构建查询条件
		Specification<Ingredient> specification = Specification.where(IngredientSpecification.hasId(id))
				.and(IngredientSpecification.hasName(name))
				.and(IngredientSpecification.hasUnitId(unitId));

		// 分页请求
		PageRequest pageable = PageRequest.of(page, size);

		// 查询结果
		Page<Ingredient> pageResult = ingredientRepository.findAll(specification, pageable);

		// 将 Ingredient 转换为 IngredientSummaryDTO
		return pageResult.map(ingredient -> convertToSummaryDTO(ingredient));
	}

	@Override
	@SuppressWarnings("null")
	public IngredientSummaryDTO getIngredientById(Long id) {
		Ingredient ingredient = ingredientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ingredient not found with id " + id));

		return convertToSummaryDTO(ingredient);
	}

	@Override
	public List<Ingredient> getIngredientByName(String name) {
		return ingredientRepository.findByName(name);
	}

	@Override
	public Ingredient createIngredient(IngredientBodyDTO ingredientDTO) {
		Ingredient ingredient = new Ingredient();
		ingredient.setName(ingredientDTO.getName());
		Dictionary unit = new Dictionary();
		unit.setId(ingredientDTO.getUnitId());
		ingredient.setUnit(unit);
		return ingredientRepository.save(ingredient);
	}

	@Override
	@SuppressWarnings("null")
	public Ingredient updateIngredient(IngredientBodyDTO ingredientDTO) {
		Dictionary unit = new Dictionary();
		unit.setId(ingredientDTO.getUnitId());
		return ingredientRepository.findById(ingredientDTO.getId())
				.map(existingIngredient -> {
					existingIngredient.setName(ingredientDTO.getName());
					existingIngredient.setUnit(unit);
					Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);
					return updatedIngredient;
				})
				.orElseThrow(() -> new ResourceNotFoundException("ingredient not found with id " + ingredientDTO.getId()));
	}

	@Override
	@SuppressWarnings("null")
	public void deleteIngredient(Long id) {
		ingredientRepository.deleteById(id);
	}

	@Override
	@Transactional
	@SuppressWarnings("null")
	public void deleteIngredients(List<Long> ids) {
		ingredientRepository.deleteAllByIdInBatch(ids);
	}

	// 转换方法
	private IngredientSummaryDTO convertToSummaryDTO(Ingredient ingredient) {
		IngredientSummaryDTO dto = new IngredientSummaryDTO();
		dto.setId(ingredient.getId());
		dto.setName(ingredient.getName());
		dto.setUnitId(ingredient.getUnit().getId().toString()); // 假设 Unit 存在且已加载
		dto.setUnitName(ingredient.getUnit().getName());
		return dto;
	}

}
