package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe extends NamedEntity {
	@Column(name = "description")
	private String description;

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	private LocalDateTime createTime;

	@LastModifiedDate
	@Column(name = "update_time")
	private LocalDateTime updateTime;
}
