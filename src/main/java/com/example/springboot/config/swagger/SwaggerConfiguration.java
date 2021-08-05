package com.example.springboot.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    final Contact contact = new Contact(
            "YeonCheol",
            "https://github.com/YeonCheolGit",
            "yeoncheol.jang@gmail.com"
    );

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .host("146.56.161.75")
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springboot.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot REST API Documentation")
                .description("장연철 게시판 개발에 사용되는 서버 API 연동 문서입니다.")
                .contact(contact)
                .license("YeonCheol")
                .version("1")
                .build();
    }
}
