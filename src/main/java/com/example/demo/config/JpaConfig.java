package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @Configuration 注解用于定义配置类，可替换 XML 配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
@Configuration
// @EnableJpaAuditing 注解用于开启 JPA 审计功能，自动填充创建时间和更新时间。如果项目比较简单，将
// @EnableJpaAuditing 放在主应用类上即可
@EnableJpaAuditing
public class JpaConfig {

}
