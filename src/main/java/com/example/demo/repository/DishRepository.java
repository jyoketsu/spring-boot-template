package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {
	@Query("SELECT d FROM Dish d WHERE d.category.dictCode = :categoryCode ORDER BY function('RAND') LIMIT :count")
	List<Dish> findRandomByCategoryCode(String categoryCode, Integer count);
}
