package com.example.demo.service;

import java.util.Optional;

import com.example.demo.dto.auth.AuthRequestDTO;
import com.example.demo.model.User;

public interface AuthService {
	public User register(AuthRequestDTO request);

	public Optional<User> findByUsername(String username);
}
