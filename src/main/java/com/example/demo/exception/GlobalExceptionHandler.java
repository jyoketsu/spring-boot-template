package com.example.demo.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		// 打印异常日志
		e.printStackTrace();
		ApiResponse<Void> response = ApiResponse.error(500, "Internal Server Error: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException e) {
		ApiResponse<Void> response = ApiResponse.error(404, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	// 处理唯一约束异常
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		ApiResponse<Void> response = ApiResponse.error(409, "Conflict: Duplicate entry detected.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	// 处理 JPA 的唯一约束异常
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException e) {
		ApiResponse<Void> response = ApiResponse.error(400, "Constraint violation: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
