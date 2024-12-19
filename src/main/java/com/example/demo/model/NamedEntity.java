package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;

@MappedSuperclass
public class NamedEntity extends BaseEntity {
	@Column(name = "name", unique = true)
	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
