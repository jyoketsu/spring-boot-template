package com.example.demo.dto;

public class ApiResponse<T> {
	private int code;
	private String message;
	private T data;

	// 构造函数
	public ApiResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	// 工厂方法
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(200, "Success", data);
	}

	public static <T> ApiResponse<T> error(int code, String message) {
		return new ApiResponse<>(code, message, null);
	}

	public T getData() {
		return data;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
