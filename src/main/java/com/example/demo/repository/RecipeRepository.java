package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.recipe.RecipeProjection;
import com.example.demo.dto.recipe.RecipeSummaryDTO;
import com.example.demo.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
	@Query("SELECT new com.example.demo.dto.recipe.RecipeSummaryDTO(r.name, r.updateTime) FROM Recipe r")
	List<RecipeSummaryDTO> findAllWithSummaryUseJPQL();

	// 当方法返回类型是一个投影接口（如 RecipeProjection），JPA 会根据方法名解析出对应的 JPQL 查询。
	// 只选择接口中定义的字段，而不是加载整个实体，从而提高性能。
	// 无需 @Query：方法名足够描述查询逻辑，且返回接口投影或实体列表。
	// 需要 @Query：查询逻辑超出方法名的表达能力，或需要使用复杂的 JPQL/原生 SQL 查询。
	// findAllBy 是一种常见的查询方法前缀，用于表示“获取所有符合某些条件的记录”。
	// 它的后缀 By 通常用于添加筛选条件，但如果没有条件，findAllBy 会被解释为获取所有记录。
	// findAllBy 和 findAll 的区别：功能相同，但 findAllBy 更语义化，表明可以扩展为按条件查询。
	// 用于投影时，两者均可用。
	List<RecipeProjection> findAllBy();

	// AS 用于将查询的结果字段名与投影接口的 getter 方法对应起来。
	// 如果字段名和投影接口方法名一致，可以省略 AS
	// SELECT r.name AS name, r.update_time AS updateTime FROM recipes r
	// 隐藏表的别名： SELECT name, update_time as updateTime FROM recipes
	@Query(value = "SELECT name, update_time as updateTime FROM recipes", nativeQuery = true)
	List<RecipeProjection> findAllWidthSummaryUseNativeSQL();

	@Query("SELECT r.name AS name, r.updateTime AS updateTime FROM Recipe r")
	Page<RecipeProjection> findSummaryPaged(Pageable pageable);
}
