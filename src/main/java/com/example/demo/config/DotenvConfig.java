package com.example.demo.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
	static {
		String env = System.getenv("SPRING_PROFILES_ACTIVE");
		// 只有在 dev 环境下才加载 .env 文件
		if (env == null || env.contains("dev")) {
			// 加载 .env 中的变量到系统环境
			Dotenv dotenv = Dotenv.load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
			System.out.println("🔧 已加载 .env 文件");
		} else {
			System.out.println("🌍 检测到生产环境，不加载 .env");
		}
	}
}
