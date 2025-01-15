package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@MappedSuperclass
public class NamedEntity extends BaseEntity {
	@Column(name = "name", unique = true)
	@NotEmpty
	private String name;
}
