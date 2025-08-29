package com.movies.moviemanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Management API")
                        .version("1.0")
                        .description("CRUD API for managing movies"));
    }
}
