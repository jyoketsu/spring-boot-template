package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.demo.dto.ApiResponse;

@ControllerAdvice
@Order(2) // 优先级较高，后执行
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// 获取当前方法的返回类型
		MediaType contentType = returnType.getContainingClass().getAnnotation(RestController.class) != null
				? MediaType.APPLICATION_JSON
				: MediaType.ALL;

		// 如果是图片或二进制内容，跳过拦截
		return !(contentType.includes(MediaType.IMAGE_PNG) || contentType.includes(MediaType.IMAGE_JPEG)
				|| contentType.includes(MediaType.APPLICATION_OCTET_STREAM));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		// 获取当前请求的 URI，判断是否为 Swagger 路径
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			String path = (String) requestAttributes.getAttribute("javax.servlet.forward.request_uri",
					RequestAttributes.SCOPE_REQUEST);
			if (path == null) {
				path = request.getURI().getPath();
			}

			// 如果是二进制流，直接返回原始 body
			if (selectedContentType.includes(MediaType.IMAGE_PNG)
					|| selectedContentType.includes(MediaType.IMAGE_JPEG)
					|| selectedContentType.includes(MediaType.APPLICATION_OCTET_STREAM)) {
				return body;
			}

			// 如果是 Swagger 相关路径，直接返回原始 body
			if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || path.startsWith("/swagger-resources")) {
				return body;
			}
		}

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
