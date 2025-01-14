package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.auth.AuthRequestDTO;
import com.example.demo.dto.auth.AuthResponseDTO;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.util.JwtUtils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/register")
	public User register(@RequestBody @Valid AuthRequestDTO request) {
		return authService.register(request);
	}

	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody @Valid AuthRequestDTO request) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					request.getUsername(), request.getPassword());

			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String jwt = jwtUtils.generateToken(userDetails);

			String username = userDetails.getUsername();
			User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
			AuthResponseDTO response = new AuthResponseDTO();
			response.setToken(jwt).setUsername(username).setRole(user.getRole()).setAvatar(user.getAvatar());
			return response;
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid username or password");
		}
	}

	@GetMapping
	public AuthResponseDTO getUserByToken(@RequestParam String token) {
		String username = jwtUtils.getUsernameFromToken(token);
		User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		AuthResponseDTO response = new AuthResponseDTO();
		response.setToken(token).setUsername(username).setRole(user.getRole()).setAvatar(user.getAvatar());
		return response;
	}

}
