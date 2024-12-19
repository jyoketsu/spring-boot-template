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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
