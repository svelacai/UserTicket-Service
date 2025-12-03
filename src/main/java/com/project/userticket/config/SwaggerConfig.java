package com.project.userticket.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public OpenAPI openAPI() {
        log.info("Configurando OpenAPI/Swagger");
        return new OpenAPI()
                .info(new Info()
                        .title("UserTicket Service API")
                        .version("1.0")
                        .description("API para gestión de usuarios y tickets"));
    }
}
