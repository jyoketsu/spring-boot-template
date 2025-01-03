package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/hello-world")
	public ResponseEntity<ApiResponse<Hello>> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return ResponseEntity.ok(ApiResponse.success(new Hello(counter.incrementAndGet(), String.format(template, name))));
	}
}
