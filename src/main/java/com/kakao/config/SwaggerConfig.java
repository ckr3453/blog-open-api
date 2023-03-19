package com.kakao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @file name : SwaggerConfiguration.java
 * @project : blog
 * @date : Mar 18, 2023
 * @author : "ckr"
 * @history:
 * @program comment :
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.kakao.domain"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
            .title("blog search REST API")
            .description("카카오뱅크 백엔드 개발자 사전과제 블로그검색 api")
            .version("0.0.1")
            .termsOfServiceUrl("Terms of service")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
            .build();
	}

    @Bean
    public UiConfiguration uiConfiguration(){
        return UiConfigurationBuilder
            .builder()
            .operationsSorter(OperationsSorter.ALPHA)
            .build();
    }
}
