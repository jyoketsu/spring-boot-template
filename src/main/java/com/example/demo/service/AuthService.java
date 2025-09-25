package com.example.demo.service;

import java.util.Optional;

import com.example.demo.dto.auth.AuthRequestDTO;
import com.example.demo.dto.auth.UpdateUserDTO;
import com.example.demo.model.User;

public interface AuthService {
	User register(AuthRequestDTO request);

	User loginByWechat(String code);

	Optional<User> findByUsername(String username);

	User updateUser(UpdateUserDTO user);
}
