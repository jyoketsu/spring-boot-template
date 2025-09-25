package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeChatLoginDTO {
	@NotBlank(message = "code is required")
	private String code;
}
