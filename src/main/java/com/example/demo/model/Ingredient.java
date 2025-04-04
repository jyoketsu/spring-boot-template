package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "ingredients", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name")
})
public class Ingredient extends NamedEntity {
	private static final long serialVersionUID = 1L;

	// 多对一：多个当前实体对象(食材)可以对应一个关联的目标实体对象(单位)
	@ManyToOne
	// name: 指定当前表中的外键字段名称
	// referencedColumnName：指定关联表（目标实体）的主键或唯一键字段名称。
	@JoinColumn(name = "unit_id", referencedColumnName = "id")
	private Dictionary unit;

	// 注释说明参考Recipe.java
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore // 避免无限递归
	private List<RecipeIngredient> recipeIngredients;
}
