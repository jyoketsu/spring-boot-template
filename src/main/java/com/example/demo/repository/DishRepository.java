package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {
	@Query("""
			SELECT DISTINCT d FROM Dish d
			LEFT JOIN FETCH d.recipes r
			WHERE d.category.dictCode = :categoryCode
			ORDER BY function('RAND')
			""")
	List<Dish> findRandomByCategoryCode(String categoryCode, Pageable pageable);
}
