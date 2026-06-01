package br.com.douglas.petshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server localhost = new Server();
        localhost.setUrl("http://localhost:8080");
        localhost.setDescription("Ambiente Local");

        //Server homolog = new Server();
        //homolog.setUrl("https://homolog.api.vitrine1.com.br");
        //homolog.setDescription("Ambiente Homologação");

        //Server production = new Server();
        //production.setUrl("https://api.vitrine1.com.br");
        //production.setDescription("Ambiente Produção");

        return new OpenAPI()
                .info(new Info()
                        .title("Petshop API")
                        .version("1.0")
                        .description("Documentação da API de gerenciamento do PetShop"))
                .servers(List.of(
                        localhost
                        //,homolog
                        //,production
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
