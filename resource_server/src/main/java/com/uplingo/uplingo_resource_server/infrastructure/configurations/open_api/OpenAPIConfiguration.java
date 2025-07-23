package com.uplingo.uplingo_resource_server.infrastructure.configurations.open_api;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Uplingo API", version = "v1", contact = @Contact(name = "Marcos")))
public class OpenAPIConfiguration {
}
