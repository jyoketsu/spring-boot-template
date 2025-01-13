package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe extends NamedEntity {
	@Column(name = "description")
	private String description;

	// 长文本（超过几千字符）使用@Column(columnDefinition = "TEXT") 来强制指定映射为 MySQL 的 TEXT 类型
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	/**
	 * mappedBy = "recipe"：指定了这个关系的反向引用，也就是说，在 RecipeIngredient 类中，有一个字段名为
	 * recipe，它引用了 Recipe 类的实例。
	 * 由于 @OneToMany 注解已经声明了关系，因此不需要使用 @Column 注解来声明字段与数据库表中的列之间的映射关系。
	 * 
	 * cascade = CascadeType.ALL：指定了级联操作的类型，这里是所有类型（ALL），也就是说，如果 Recipe
	 * 实例发生变化（如保存、更新、删除），这些变化也会级联到相关的 RecipeIngredient 实例。
	 * 
	 * orphanRemoval = true：指定了当 Recipe 实例被删除时，相关的 RecipeIngredient 实例是否也应该被删除。如果为
	 * true，则会删除相关的 RecipeIngredient 实例。
	 */
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<RecipeIngredient> recipeIngredients;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
}
