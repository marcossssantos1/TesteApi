package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.GarageResponse;
import com.teste.marcossantos.Simulador.de.api.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/garage")
public class GarageController {

    @Autowired
    private final GarageService garageService;
    @Autowired
    private final RestTemplate restTemplate;

    public GarageController(GarageService garageService, RestTemplate restTemplate) {
        this.garageService = garageService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/sync")
    public ResponseEntity<String> syncGarageData() {
        GarageResponse response = restTemplate.getForObject("http://localhost:8080/garage", GarageResponse.class);
        garageService.saveGarageData(response);
        return ResponseEntity.ok("Dados salvos no banco.");
    }
}
