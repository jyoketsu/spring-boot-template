package com.example.demo.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api")
public class CaptchaController {

	@Autowired
	private DefaultKaptcha kaptchaProducer;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@GetMapping("/captcha")
	@SuppressWarnings("null")
	public void getCaptcha(HttpServletResponse response) throws IOException {
		// 生成验证码文本
		String captchaText = kaptchaProducer.createText();

		// 生成唯一标识符，区分不同的验证码
		String captchaId = UUID.randomUUID().toString();
		
		// 保存验证码文本到Redis, 设置过期时间为5分钟
		redisTemplate.opsForValue().set(captchaId, captchaText, 5, TimeUnit.MINUTES);

		// 生成验证码图片
		BufferedImage captchaImage = kaptchaProducer.createImage(captchaText);

		// 设置响应类型
		response.setContentType("image/png");

		// 将唯一标识符添加到响应头
		response.setHeader("Captcha-Id", captchaId);

		// 将图片写入响应输出流
		ImageIO.write(captchaImage, "png", response.getOutputStream());
	}
}