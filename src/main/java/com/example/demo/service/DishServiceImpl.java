package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.dish.DishBodyDTO;
import com.example.demo.dto.dish.DishListDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Dictionary;
import com.example.demo.model.Dish;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.DishSpecification;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DishServiceImpl implements DishService {

	private final DishRepository dishRepository;

	public DishServiceImpl(DishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	@Override
	public Page<DishListDTO> getAllDishes(String name, Long categoryId, Integer page, Integer size) {
		Specification<Dish> specification = Specification.where(DishSpecification.hasName(name))
				.and(DishSpecification.hasCategoryId(categoryId));

		PageRequest pageable = PageRequest.of(page, size);

		Page<Dish> pageResult = dishRepository.findAll(specification, pageable);

		return pageResult.map(ingredient -> convertToListDTO(ingredient));
	}

	private DishListDTO convertToListDTO(Dish dish) {
		DishListDTO dto = new DishListDTO();
		dto.setId(dish.getId());
		dto.setName(dish.getName());
		dto.setCategoryId(dish.getCategory().getId().toString());
		dto.setCategoryName(dish.getCategory().getName());
		return dto;
	}

	@Override
	public Dish createDish(DishBodyDTO dishBodyDTO) {
		Dish dish = new Dish();
		dish.setName(dishBodyDTO.getName());
		dish.setDescription(dishBodyDTO.getDescription());
		dish.setCover(dishBodyDTO.getCover());

		Dictionary category = new Dictionary();
		category.setId(dishBodyDTO.getCategoryId());
		dish.setCategory(category);

		return dishRepository.save(dish);
	}

	@Override
	public Dish updateDish(DishBodyDTO dishBodyDTO) {
		Dictionary category = new Dictionary();
		category.setId(dishBodyDTO.getCategoryId());
		return dishRepository.findById(dishBodyDTO.getId())
				.map(existingDish -> {
					existingDish.setName(dishBodyDTO.getName());
					existingDish.setDescription(dishBodyDTO.getDescription());
					existingDish.setCover(dishBodyDTO.getCover());
					existingDish.setCategory(category);
					Dish updatedDish = dishRepository.save(existingDish);
					return updatedDish;
				})
				.orElseThrow(() -> new ResourceNotFoundException("dish not found with id " + dishBodyDTO.getId()));
	}

	@Override
	public void deleteDish(Long id) {
		dishRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteDishes(List<Long> ids) {
		dishRepository.deleteAllByIdInBatch(ids);
	}

}
