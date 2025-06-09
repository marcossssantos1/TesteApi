package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleEventDTO;
import com.teste.marcossantos.Simulador.de.api.dto.WebhookDTO;
import com.teste.marcossantos.Simulador.de.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/entry")
    public ResponseEntity<String> handleEntry(@RequestBody VehicleEventDTO dto) {
        vehicleService.processEntry(dto);
        return ResponseEntity.ok("Entrada registrada");
    }

    @PostMapping("parked")
    public ResponseEntity<String> handleParked(@RequestBody WebhookDTO dto) {
        vehicleService.handleParked(dto.getLicense_plate(), dto.getLat(), dto.getLng());
        return ResponseEntity.ok("Estacionamento registrado");
    }
}
