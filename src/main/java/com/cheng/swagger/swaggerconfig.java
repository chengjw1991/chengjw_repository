package com.cheng.swagger;

import com.cheng.token.JwtData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/20/020
 * swagger 配置类
 */
@Configuration
public class swaggerconfig {
    @Bean
    public Docket createAPI(){
        List<Parameter> list = new ArrayList<>();
        ParameterBuilder builder = new ParameterBuilder();
        builder.name(JwtData.JWT_NAME).description("token入口").modelRef(new ModelRef("String")).parameterType("header").required(false);
        list.add(builder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("123")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cheng.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(list);

    }
    public ApiInfo apiInfo(){
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("这是一个Swagger测试api");
        builder.description("Swagger测试用的api");
        return builder.build();
    }

}
