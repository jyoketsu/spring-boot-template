package com.example.demo.dto.auth;

import com.example.demo.enums.Role;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthResponseDTO {
	private Long id;
	private String username;
	private Role role;
	private String avatar;
}
