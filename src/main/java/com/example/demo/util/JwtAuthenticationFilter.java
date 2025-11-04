package com.example.demo.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	/**
	 * 处理 HTTP 请求，通过解析 Authorization 头中的 JWT 令牌进行用户认证。
	 * 如果令牌有效，则通过在 SecurityContext 中设置 Authentication 对象来认证用户。
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// 获取token
		String token = parseJwt(request);
		// String token = getJwtFromCookies(request);

		if (token != null && jwtUtils.validateToken(token)) {
			// 从token中获取用户信息
			String username = jwtUtils.getUsernameFromToken(token);

			// 从数据库中获取用户信息
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// 设置权限
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					// 获取用户所有权限 管理员：[ROLE_ADMIN]
					userDetails.getAuthorities());

			// 设置SecurityContext（存储权限上下文）
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// 继续过滤
		filterChain.doFilter(request, response);
	}

	/**
	 * 从 Authorization 头中解析 JWT 令牌。如果令牌有效，则返回令牌，否则返回 null。
	 * 
	 * @param request the HttpServletRequest
	 * @return the parsed JWT token, or null if invalid or missing
	 */
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private String getJwtFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("accessToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
