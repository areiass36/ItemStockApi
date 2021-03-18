package com.barretoareias.itemStock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.barretoareias.itemStock.controller";
    private static final String API_TITLE = "Item Stock API";
    private static final String API_DESCRIPTION = "REST API for stocking items";
    private static final String CONTACT_NAME = "Augusto Barreto";
    private static final String CONTACT_GITHUB = "https://gtihub.com/barretoareias";
    private static final String CONTACT_LINKEDIN = "https://www.linkedin.com/in/augusto-barreto-a72331178/";
    private static final String CONTACT_EMAIL = "barretoareias@gmail.com";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version("0.0.1")
                .contact(new Contact(CONTACT_NAME,CONTACT_GITHUB,CONTACT_EMAIL))
                .build();
    }
}
