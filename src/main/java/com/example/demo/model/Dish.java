package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜品
 */
@Entity
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "dishes")
public class Dish extends NamedEntity {
	
	/**
	 * 描述
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 封面
	 */
	@Column(name = "cover")
	private String cover;

	/**
	 * 分类
	 */
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Dictionary category;

	/**
	 * 菜谱
	 */
	@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Recipe> recipes;
}
