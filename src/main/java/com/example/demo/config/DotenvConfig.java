package com.example.demo.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "dev", "local" }) // 只有 dev/local 环境才加载
@Configuration
public class DotenvConfig {
	static {
		// 加载 .env 中的变量到系统环境
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		System.out.println("🔧 已加载 .env 文件");
	}
}
