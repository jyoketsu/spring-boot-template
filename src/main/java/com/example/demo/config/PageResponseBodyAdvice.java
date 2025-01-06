package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.demo.dto.PageResponse;

@ControllerAdvice
@Order(1) // 优先级较低，先执行
public class PageResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return Page.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			org.springframework.http.server.ServerHttpRequest request,
			org.springframework.http.server.ServerHttpResponse response) {
		if (body instanceof Page) {
			Page<?> page = (Page<?>) body;
			// return ApiResponse.success(PageResponse.of(page));
			return PageResponse.of(page);
		}
		return body;
	}
}
