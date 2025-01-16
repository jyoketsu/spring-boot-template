package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateUserDTO {
	@NotBlank(message = "Id is required")
	private Long id;
	@NotBlank(message = "Username is required")
	private String username;
	private String avatar;
	private String password;
}
