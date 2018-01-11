package com.november;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类 ：用于将文档和代码整合起来，并提供了页面测试功能
 * TODO
 * @author yangzexu
 * 2018年1月9日
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket creatRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("RestfulApi")
				.genericModelSubstitutes(DeferredResult.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(PathSelectors.any())//过滤的接口
                .build()
                .apiInfo(apiInfo());
	}
	
	public ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("REST API")
				.version("1.0")
				.build();
	}
}
