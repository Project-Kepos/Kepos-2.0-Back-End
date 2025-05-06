package com.kepos.backend.config

import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun apiInfo(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("v1")
            .pathsToMatch("/api/v1/**")
            .build()
    }

    @Bean
    fun customOpenAPI(): io.swagger.v3.oas.models.OpenAPI {
        return io.swagger.v3.oas.models.OpenAPI()
            .info(
                Info()
                    .title("Kepos API")
                    .description("Documentação da API Kepos")
                    .version("1.0")
                    .contact(
                        Contact()
                            .name("Equipe Kepos")
                            .email("contato@kepos.com")
                    )
            )
    }
}