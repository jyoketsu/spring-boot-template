package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RefreshTokenRequestDTO {
	@NotBlank(message = "refreshToken不能为空")
	private String refreshToken;
}
