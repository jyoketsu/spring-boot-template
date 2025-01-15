package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.Accessors;

// @MappedSuperclass 是 JPA 提供的一个注解，用于定义继承映射的基类。主要特点：
// 不会生成对应的数据库表
// 基类字段映射到子类对应的表中：
// 通常用于代码复用
@MappedSuperclass
// @EntityListeners 注解用于指定一个或多个实体监听器类，这些类中的回调方法将在实体上发生的生命周期事件发生时被调用。
@EntityListeners(AuditingEntityListener.class)
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {
	@Id
	// 数据库的自增主键
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(name = "create_time", nullable = true, updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	@LastModifiedDate
	@Column(name = "update_time", nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	// 必须有一个无参构造函数（可以是显式声明的或隐式的）。这是因为 JPA 在实例化实体类时，需要通过反射来创建对象，而反射需要调用无参构造函数。
	// 如果你没有声明任何构造函数，Java 会默认提供一个无参构造函数。但如果你声明了其他参数化构造函数，就需要显式地定义无参构造函数。
}
