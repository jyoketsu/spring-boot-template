package com.example.demo.exception;

/**
 * 禁止访问异常，表示 403 错误
 */
public class ForbiddenException extends RuntimeException {

	// 默认构造函数
	public ForbiddenException(String message) {
		super(message);
	}

	// 可以根据需要添加其他构造函数
	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}
}
