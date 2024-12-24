package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient extends NamedEntity {

	// 多对一：多个当前实体对象可以对应一个关联的目标实体对象
	@ManyToOne
	// name: 指定当前表中的外键字段名称
	// referencedColumnName：指定关联表（目标实体）的主键或唯一键字段名称。
	@JoinColumn(name = "unit_id", referencedColumnName = "id")
	private Dictionary unit;

	public Dictionary getUnit() {
		return unit;
	}

	public void setUnit(Dictionary unit) {
		this.unit = unit;
	}
}
