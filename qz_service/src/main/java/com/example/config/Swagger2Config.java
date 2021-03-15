package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*paramType：表示参数放在哪个地方
    header-->请求参数的获取：@RequestHeader(代码中接收注解)
    query-->请求参数的获取：@RequestParam(代码中接收注解)
    path（用于restful接口）-->请求参数的获取：@PathVariable(代码中接收注解)
    body-->请求参数的获取：@RequestBody(代码中接收注解)
    form（不常用）*/
//http://localhost:8001/swagger-ui.html
@Configuration // 让Spring来加载该类配置
@EnableSwagger2 // 启用Swagger2
public class Swagger2Config {

	/**
	 * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 *
	 * @return Docket
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 此包路径下的类，才生成接口文档
				.apis(RequestHandlerSelectors.basePackage("com.example.api"))
				// 加了ApiOperation注解的类，才生成接口文档
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
				.build();
	}

	/**
	 * api文档的详细信息函数,注意这里的注解引用的是哪个
	 *
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// //大标题
				.title("泉州地方文化网站接口")
				// 版本号
				.version("1.0")
//                .termsOfServiceUrl("NO terms of service")
				// 描述
				.description("后台服务API接口文档")
				// 作者
				.contact("YYH ")
				// .contact(new Contact("admin", " ", " "))
				// .license("The Apache License, Version 2.0")
				// .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}

}
