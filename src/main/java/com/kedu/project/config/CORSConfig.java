package com.kedu.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 서버의 어떤 url 로 요청을 하든
		.allowedOrigins("http://10.5.5.19:3000") // 모든 출처가 아닌 정해진 곳에서만 허락.
		.allowedMethods("*")	   // 모든 요청 방식
		.allowedHeaders("*")	   // 모든 요청 헤더에 대해 전부 허가 하겠음.
		.allowCredentials(true);	// 작업 증명(setCookie) 을 허용하겠다.
//		이 서버에 들어오는 모든 리퀘스트 매핑 요청들에 대하여 허락해준다.
	}
}
