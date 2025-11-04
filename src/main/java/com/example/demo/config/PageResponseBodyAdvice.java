package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.demo.dto.PageResponse;

@ControllerAdvice
@Order(1) // 优先级较低，先执行
public class PageResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(@NonNull MethodParameter returnType,
			@NonNull Class<? extends HttpMessageConverter<?>> converterType) {
		return Page.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType,
			@NonNull MediaType selectedContentType,
			@NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
			@NonNull ServerHttpRequest request,
			@NonNull ServerHttpResponse response) {
		if (body instanceof Page) {
			Page<?> page = (Page<?>) body;
			// return ApiResponse.success(PageResponse.of(page));
			return PageResponse.of(page);
		}
		return body;
	}
}
