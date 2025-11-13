package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "meal_rule")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MealRule extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "rule_json", columnDefinition = "TEXT")
	private String ruleJson; // 存 JSON 规则，如 {"big_meat":1,"small_meat":1,"vegetable":1,"soup":1}
}
