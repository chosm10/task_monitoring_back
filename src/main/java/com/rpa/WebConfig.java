package com.rpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 여기서 origin에 들어가는 주소는 요청하는 클라이언트의 주소임!!!!!!!!!!!
		registry.addMapping("/**").allowedMethods("*").allowCredentials(false).allowedHeaders("*").allowedOrigins("*");
	}
}
