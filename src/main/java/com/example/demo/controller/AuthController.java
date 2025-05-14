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
import com.example.demo.dto.auth.LogoutRequestDTO;
import com.example.demo.dto.auth.RefreshTokenRequestDTO;
import com.example.demo.dto.auth.UpdateUserDTO;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtils;

// import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Autowired
	private CustomUserDetailsService userDetailsService;

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
			@RequestBody @Valid AuthRequestDTO request, HttpServletResponse response) {
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
			String accessToken = jwtUtils.generateAccessToken(userDetails);
			String refreshToken = jwtUtils.generateRefreshToken(userDetails);

			// // 设置 HttpOnly 和 Secure 属性的 Cookie
			// Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
			// accessTokenCookie.setHttpOnly(true);
			// accessTokenCookie.setSecure(false);
			// accessTokenCookie.setPath("/");
			// accessTokenCookie.setMaxAge(7200); // 2小时

			// Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
			// refreshTokenCookie.setHttpOnly(true);
			// refreshTokenCookie.setSecure(false);
			// // refreshTokenCookie 只有在访问 /api/auth/refresh 路径时才会被发送
			// refreshTokenCookie.setPath("/api/auth/token");
			// refreshTokenCookie.setMaxAge(604800); // 7天

			// response.addCookie(accessTokenCookie);
			// response.addCookie(refreshTokenCookie);

			// 获取用户名并查找用户
			String username = userDetails.getUsername();
			User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

			return new AuthResponseDTO()
					.setUsername(username)
					.setRole(user.getRole())
					.setAvatar(user.getAvatar())
					.setAccessToken(accessToken)
					.setRefreshToken(refreshToken);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid username or password");
		}
	}

	@PostMapping("/token/refresh")
	public AuthResponseDTO refresh(@RequestBody @Valid RefreshTokenRequestDTO request, HttpServletResponse response) {
		String refreshToken = request.getRefreshToken();
		// 验证令牌有效性
		if (!jwtUtils.validateToken(refreshToken) ||
				jwtUtils.isTokenBlacklisted(refreshToken) ||
				!jwtUtils.isRefreshToken(refreshToken)) {
			throw new UnauthorizedException("Invalid refresh token");
		}

		// 将旧的 refresh token 加入黑名单
		jwtUtils.addToBlacklist(refreshToken);

		String username = jwtUtils.getUsernameFromToken(refreshToken);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		// 生成新的 token
		String newAccessToken = jwtUtils.generateAccessToken(userDetails);
		String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);

		return new AuthResponseDTO()
				.setAccessToken(newAccessToken)
				.setRefreshToken(newRefreshToken);
	}

	@PostMapping("/token/logout")
	public void logout(@RequestBody @Valid LogoutRequestDTO request) {
		String accessToken = request.getAccessToken();
		String refreshToken = request.getRefreshToken();

		if (accessToken != null) {
			jwtUtils.addToBlacklist(accessToken);
		}
		if (refreshToken != null) {
			jwtUtils.addToBlacklist(refreshToken);
		}
	}

	/**
	 * 根据令牌获取用户信息
	 * 
	 * @param Authorization JWT令牌
	 * @return 认证响应数据
	 */
	@GetMapping
	public AuthResponseDTO getUserByToken(@RequestHeader("Authorization") String authorizationHeader) {
		// 从Header中提取Bearer token
		String token = authorizationHeader.replace("Bearer ", "");
		// 从令牌中获取用户名
		String username = jwtUtils.getUsernameFromToken(token);
		// 根据用户名查找用户
		User user = authService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		// 构建响应对象
		AuthResponseDTO response = new AuthResponseDTO();
		response
				.setId(user.getId())
				.setUsername(username)
				.setRole(user.getRole())
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
