package com.teste.marcossantos.Simulador.de.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SimuladorDeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimuladorDeApiApplication.class, args);
	}

}
