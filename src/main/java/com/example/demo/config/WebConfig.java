package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
	@Bean
	@SuppressWarnings("null")
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);

		/**
		 * 解决 RestTemplate 不支持 text/plain 类型的问题
		 */
		for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				List<MediaType> mediaTypes = new ArrayList<>(jsonConverter.getSupportedMediaTypes());
				mediaTypes.add(MediaType.TEXT_PLAIN);
				jsonConverter.setSupportedMediaTypes(mediaTypes);
			}
		}

		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(15000);
		// 设置代理
		// factory.setProxy(null);
		return factory;
	}
}