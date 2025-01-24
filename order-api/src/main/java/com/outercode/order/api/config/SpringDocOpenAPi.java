package com.outercode.order.api.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenAPi {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest api - Orders")
                                .description("API REST from orders")
                                .contact(new Contact().name("Vladimir Monteiro Souza de Lima")
                                        .email("vladimir.monteiro021@gmail.com"))
                );

    }
}
