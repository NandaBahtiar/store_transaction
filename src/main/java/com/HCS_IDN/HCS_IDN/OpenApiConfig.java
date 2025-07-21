package com.HCS_IDN.HCS_IDN;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Dokumentasi",
                version = "1.0",
                description = "Dokumentasi API untuk sistem HCS"
        )
)
public class OpenApiConfig {
}
