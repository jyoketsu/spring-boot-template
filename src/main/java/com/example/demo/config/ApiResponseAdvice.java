package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.demo.dto.ApiResponse;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// 支持所有返回值类型的拦截
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		// 如果是 ResponseEntity 类型，处理其内部的 body
		if (body instanceof ResponseEntity<?> responseEntity) {
			Object entityBody = responseEntity.getBody();
			if (entityBody instanceof ApiResponse<?>) {
				// 如果已经是 ApiResponse，则直接返回
				return body;
			} else {
				// 否则封装为 ApiResponse
				ApiResponse<Object> wrappedBody = ApiResponse.success(entityBody);
				return ResponseEntity.status(responseEntity.getStatusCode())
						.headers(responseEntity.getHeaders())
						.body(wrappedBody);
			}
		}

		// 如果返回值已经是 ApiResponse 类型，直接返回
		if (body instanceof ApiResponse<?>) {
			return body;
		}

		// 其他类型的返回值，封装为 ApiResponse
		return ApiResponse.success(body);
	}
}
