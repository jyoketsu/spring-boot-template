package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient extends NamedEntity {

	@Column(name = "unit")
	private String unit;

	// 必须有一个无参构造函数（可以是显式声明的或隐式的）。这是因为 JPA 在实例化实体类时，需要通过反射来创建对象，而反射需要调用无参构造函数。
	// 如果你没有声明任何构造函数，Java 会默认提供一个无参构造函数。但如果你声明了其他参数化构造函数，就需要显式地定义无参构造函数。

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
