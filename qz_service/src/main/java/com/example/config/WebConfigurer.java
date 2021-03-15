package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.config.interceptor.LoginInterceptor;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	@Autowired
	private LoginInterceptor loginInterceptor;
	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns("/**") 表示拦截所有的请求，
		// excludePathPatterns("/login","/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
		// registry.addInterceptor(userLoginInterceptor).excludePathPatterns("/login/**","/register","/static/**");
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
		/*
		 * .excludePathPatterns("/api/user/login", "/api/user/register",
		 * "/api/article/getAll", "/api/user/checkCode", "/api/user/sendMimeMail",
		 * "/api/travelNotes/getAll", "/api/upload/**")
		 */;
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*").allowCredentials(true)
				.allowedMethods("POST", "GET", "DELETE", "PUT").allowCredentials(true).allowedOrigins("*")
				.exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);

	}
}
