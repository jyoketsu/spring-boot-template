package com.example.demo.exception;

/**
 * 未授权异常，表示 401 错误
 */
public class UnauthorizedException extends RuntimeException {

	// 默认构造函数
	public UnauthorizedException(String message) {
		super(message);
	}

	// 可以根据需要添加其他构造函数
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
}
