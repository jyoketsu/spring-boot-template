package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.model.Ingredient;

// CrudRepository 和 JpaRepository 是 Spring Data 提供的两种不同的 Repository 接口，它们都可以用于数据访问操作
// CrudRepository 主要用于执行基本的增删改查操作
// JpaRepository 是 CrudRepository 的子接口，提供了分页和排序功能，以及更多的 JPA 特性支持
public interface IngredientRepository extends JpaRepository<Ingredient, Long>, JpaSpecificationExecutor<Ingredient> {

	// Spring Data JPA 允许你通过在接口中声明方法签名，来定义自定义查询方法，而不需要额外编写 SQL 或 JPQL 查询代码。
	// 方法名遵循一定的规则，如 findBy + 字段名
	List<Ingredient> findByName(String name);

	Ingredient findById(long id);

}
