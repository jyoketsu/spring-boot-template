package com.example.demo.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	/**
	 * Processes the HTTP request by parsing the JWT token from the Authorization
	 * header and authenticating the user. If the token is valid, the user is
	 * authenticated by
	 * setting the Authentication object in the SecurityContext.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 获取token
		String token = parseJwt(request);
		if (token != null && jwtUtils.validateToken(token)) {
			// 从token中获取用户信息
			String username = jwtUtils.getUsernameFromToken(token);

			// 从数据库中获取用户信息
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			// 设置权限
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
			// 设置SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// 继续过滤
		filterChain.doFilter(request, response);
	}

	/**
	 * Parses the JWT token from the Authorization header. If the token is valid,
	 * returns the token, otherwise returns null.
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
}
