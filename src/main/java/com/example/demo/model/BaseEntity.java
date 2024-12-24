package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// @MappedSuperclass 是 JPA 提供的一个注解，用于定义继承映射的基类。主要特点：
// 不会生成对应的数据库表
// 基类字段映射到子类对应的表中：
// 通常用于代码复用
@MappedSuperclass
public class BaseEntity {
	@Id
	// 数据库的自增主键
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 必须有一个无参构造函数（可以是显式声明的或隐式的）。这是因为 JPA 在实例化实体类时，需要通过反射来创建对象，而反射需要调用无参构造函数。
	// 如果你没有声明任何构造函数，Java 会默认提供一个无参构造函数。但如果你声明了其他参数化构造函数，就需要显式地定义无参构造函数。

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
