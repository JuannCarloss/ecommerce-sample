package com.shop.ecommerce.configs;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApiConfigurer(){
        Info information = new Info()
                .title("E-commerce Management System API")
                .version("1.0")
                .description("This API exposes endpoints to manage an e-commerce.");

        return new OpenAPI().info(information);
    }


}
