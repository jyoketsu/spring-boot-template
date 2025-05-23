package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.auth.AuthRequestDTO;
import com.example.demo.dto.auth.UpdateUserDTO;
import com.example.demo.enums.Role;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User register(AuthRequestDTO request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username already exists");
		}

		User user = new User();
		user.setUsername(request.getUsername());
		// 加密密码
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		// 检查是否是第一个用户
		boolean isFirstUser = userRepository.count() == 0;
		// 如果是第一个用户，赋予 ADMIN 权限，否则赋予 USER 权限
		user.setRole(isFirstUser ? Role.ADMIN : Role.USER);

		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User updateUser(UpdateUserDTO user) {
		Optional<User> searchedUser = userRepository.findByUsername(user.getUsername());
		if (searchedUser.isPresent() && !searchedUser.get().getId().equals(user.getId())) {
			throw new IllegalArgumentException("Username already exists");
		}

		Long id = user.getId();
		return userRepository.findById(id)
				.map(existingUser -> {
					if (user.getUsername() != null && !user.getUsername().isEmpty()) {
						existingUser.setUsername(user.getUsername());
					}
					if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
						existingUser.setAvatar(user.getAvatar());
					}
					if (user.getPassword() != null && !user.getPassword().isEmpty()) {
						existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
					}
					User updatedUser = userRepository.save(existingUser);
					return updatedUser;
				})
				.orElseThrow(() -> new ResourceNotFoundException("user not found with id " + id));
	}
}
