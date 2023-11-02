package com.store.config;

import java.util.ArrayDeque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket docket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(getApiInfo());
		return docket;
		
	}

	private ApiInfo getApiInfo() {
		
		ApiInfo apiInfo = new ApiInfo("Electronics Store Backend: APIS ", 
				"This is backend project created by LCWD",
		"1.0.0V", "https://www.learncodewithdurgesh.com",
		new Contact("Durgesh", "https://www.instagram.com/durgesh_k_t", "learncodewithdurgesh@gmail.com"),
		"License of APIS",
		"https://www.learncodewithdurgesh.com/about",
		new ArrayDeque<>()
		
		);
		
		
		return apiInfo;
	}
	

}
