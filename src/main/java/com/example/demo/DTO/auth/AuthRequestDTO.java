package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequestDTO {
	@NotBlank(message = "Username is required")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only letters and numbers")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
}
