package com.example.demo.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

	/**
	 * 配置 Kaptcha 验证码生成器
	 * @return DefaultKaptcha 实例
	 */
	@Bean
	public DefaultKaptcha kaptchaProducer() {
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no"); // 无边框
		properties.setProperty("kaptcha.textproducer.font.color", "black"); // 字体颜色
		properties.setProperty("kaptcha.textproducer.char.space", "5"); // 字符间距
		properties.setProperty("kaptcha.image.width", "200"); // 图片宽度
		properties.setProperty("kaptcha.image.height", "50"); // 图片高度
		properties.setProperty("kaptcha.textproducer.font.size", "40"); // 字体大小
		Config config = new Config(properties);
		kaptcha.setConfig(config);
		return kaptcha;
	}
}