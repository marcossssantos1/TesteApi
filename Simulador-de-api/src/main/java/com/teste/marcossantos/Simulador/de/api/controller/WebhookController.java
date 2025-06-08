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

    @PostMapping
    public ResponseEntity<String> receiveEvent(@RequestBody VehicleEventDTO dto) {
        if ("ENTRY".equalsIgnoreCase(dto.getEvent_type())) {
            vehicleService.processEntry(dto);
            return ResponseEntity.ok("Entrada registrada");
        }
        return ResponseEntity.badRequest().body("Evento não suportado");
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveWebhook(@RequestBody WebhookDTO dto) {
        switch (dto.getEventType()) {
            case "ENTRY":
                VehicleEventDTO vehicleDTO = new VehicleEventDTO(dto.getLicensePlate(), dto.getEntryTime());
                vehicleService.processEntry(vehicleDTO);
                break;
            case "PARKED":
                vehicleService.handleParked(dto.getLicensePlate(), dto.getLat(), dto.getLng());
                break;
            // outros casos...
        }
        return ResponseEntity.ok().build();
    }
}
