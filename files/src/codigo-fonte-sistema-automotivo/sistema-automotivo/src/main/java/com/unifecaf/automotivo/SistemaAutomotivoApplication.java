package com.unifecaf.automotivo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Sistema Automotivo — Gestão de Estoque de Veículos",
        version = "1.0",
        description = "API REST para gerenciamento de estoque de veículos. " +
                      "Projeto acadêmico UniFECAF — Programação Orientada a Objetos."
    )
)
public class SistemaAutomotivoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaAutomotivoApplication.class, args);
        System.out.println("\n Sistema Automotivo iniciado com sucesso!");
        System.out.println(" Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println(" API Docs:   http://localhost:8080/api-docs\n");
    }
}
