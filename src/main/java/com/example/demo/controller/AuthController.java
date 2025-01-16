package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.example.demo.dto.auth.ChangePasswordDTO;
import com.example.demo.dto.auth.UpdateUserDTO;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.util.JwtUtils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 用户认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 用户注册
	 * 
	 * @param request 用户注册请求数据
	 * @return 注册后的用户
	 */
	@PostMapping("/register")
	public User register(@RequestBody @Valid AuthRequestDTO request) {
		return authService.register(request);
	}

	/**
	 * 用户登录
	 * 
	 * @param captchaId 验证码ID
	 * @param request   用户登录请求数据
	 * @return 认证响应数据，包括JWT令牌
	 */
	@PostMapping("/login")
	public AuthResponseDTO login(@RequestHeader("Captcha-Id") String captchaId,
			@RequestBody @Valid AuthRequestDTO request) {
		// 验证验证码
		String captcha = (String) redisTemplate.opsForValue().get(captchaId);
		if (captcha == null || !request.getCaptcha().equals(captcha)) {
			throw new RuntimeException("验证码不正确！");
		}
		try {
			// 创建认证令牌
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					request.getUsername(), request.getPassword());

			// 进行认证
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			// 设置认证信息到安全上下文
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// 获取用户详情
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			// 生成JWT令牌
			String jwt = jwtUtils.generateToken(userDetails);

			// 获取用户名并查找用户
			String username = userDetails.getUsername();
			User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

			// 构建响应对象
			AuthResponseDTO response = new AuthResponseDTO();
			response.setToken(jwt).setUsername(username).setRole(user.getRole()).setAvatar(user.getAvatar());
			return response;
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid username or password");
		}
	}

	/**
	 * 根据令牌获取用户信息
	 * 
	 * @param token JWT令牌
	 * @return 认证响应数据
	 */
	@GetMapping
	public AuthResponseDTO getUserByToken(@RequestParam String token) {
		// 从令牌中获取用户名
		String username = jwtUtils.getUsernameFromToken(token);
		// 根据用户名查找用户
		User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		// 构建响应对象
		AuthResponseDTO response = new AuthResponseDTO();
		response.setToken(token).setId(user.getId()).setUsername(username).setRole(user.getRole())
				.setAvatar(user.getAvatar());
		return response;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user 更新用户请求数据
	 * @return 更新后的用户
	 */
	@PutMapping
	public User update(@RequestBody UpdateUserDTO user) {
		return authService.updateUser(user);
	}

	/**
	 * 修改密码
	 * 
	 * @param request 修改密码请求数据
	 * @return 更新后的用户
	 */
	@PostMapping("/changePassword")
	public User changePassword(@RequestBody @Valid ChangePasswordDTO request) {
		try {
			// 创建认证令牌
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					request.getUsername(), request.getPassword());

			// 进行认证
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			// 获取用户详情
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			// 获取用户名并查找用户
			String username = userDetails.getUsername();
			User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

			// 构建更新用户请求数据
			UpdateUserDTO userDTO = new UpdateUserDTO();
			userDTO.setId(user.getId()).setPassword(request.getNewPassword());

			// 更新用户信息
			return authService.updateUser(userDTO);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid username or password");
		}
	}

}
