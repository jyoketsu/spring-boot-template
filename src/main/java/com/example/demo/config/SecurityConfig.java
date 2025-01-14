package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.util.JwtAuthenticationFilter;

@Configuration
// 启用 Spring Security
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/**
		 * 在引入 spring-boot-starter-security 之后，Spring Security 默认会保护所有的端点，你需要显式地配置
		 * Spring Security，让相关端点保持公开访问。
		 */
		http
				// 前后端分离项目通常禁用 CSRF
				.csrf(csrf -> csrf.disable())
				// 无状态，使用 JWT
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// 放行的接口（无需登录）
						.requestMatchers(
								"/swagger-ui/**",
								"/v3/api-docs/**",
								"/swagger-resources/**",
								"/webjars/**",
								"/api/auth/**")
						.permitAll()
						.anyRequest().authenticated())
				// 使用自定义的过滤器
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * 获取 AuthenticationManager
	 * AuthenticationConfiguration 是 Spring Security 提供的一个类，允许我们轻松获取
	 * AuthenticationManager。
	 * 
	 * @param authenticationConfiguration
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	// 密码加密
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
}
