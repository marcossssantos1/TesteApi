package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleParkedDTO;
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

    @PostMapping
    public ResponseEntity<Void> receiveWebhook(@RequestBody WebhookDTO dto) {
        switch (dto.getEvent_type().toUpperCase()) {
            case "ENTRY":
                VehicleEntryDTO vehicleDTO = new VehicleEntryDTO(dto.getLicense_plate(), dto.getEntry_time());
                vehicleService.processEntry(vehicleDTO);
                break;
            case "PARKED":
                vehicleService.handleParked(dto.getLicense_plate(), dto.getLat(), dto.getLng());
                break;
            case "EXIT":
                vehicleService.processExit(dto.getLicense_plate(), dto.getExitTime());
                break;
            default:
                throw new RuntimeException("Evento não suportado");
        }
        return ResponseEntity.ok().build();
    }
}
