package com.teste.marcossantos.Simulador.de.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate template(){
        return  new RestTemplate();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Estacionamento")
                        .description("Documentação dos Webhooks de Entrada, Estacionamento e Saída")
                        .version("1.0.0"));
    }
}