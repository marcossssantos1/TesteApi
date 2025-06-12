package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.ParkedResponse;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryResponse;
import com.teste.marcossantos.Simulador.de.api.dto.WebhookDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleExitResponse;
import com.teste.marcossantos.Simulador.de.api.service.VehicleExitService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleParkedService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleEntryService;
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
    private VehicleEntryService vehicleEntryService;

    @Autowired
    private VehicleParkedService vehicleParkedService;

    @Autowired
    private VehicleExitService vehicleExitService;

    @PostMapping
    public ResponseEntity<?> receiveWebhook(@RequestBody WebhookDTO dto) {
        String eventType = dto.getEvent_type().toUpperCase();

        switch (eventType) {
            case "ENTRY":
                return handleEntry(dto);
            case "PARKED":
                return handleParked(dto);
            case "EXIT":
                return handleExit(dto);
            default:
                return ResponseEntity.badRequest().body("Evento não suportado: " + dto.getEvent_type());
        }
    }

    private ResponseEntity<VehicleEntryResponse> handleEntry(WebhookDTO dto) {
        VehicleEntryDTO entryDTO = new VehicleEntryDTO(dto.getLicense_plate(), dto.getEntry_time());
        VehicleEntryResponse response = vehicleEntryService.processEntry(entryDTO);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ParkedResponse> handleParked(WebhookDTO dto) {
        ParkedResponse response = vehicleParkedService.handleParked(dto.getLicense_plate(), dto.getLat(), dto.getLng());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<VehicleExitResponse> handleExit(WebhookDTO dto) {
        VehicleExitResponse response = vehicleExitService.processExit(dto.getLicense_plate(), dto.getExitTime());
        return ResponseEntity.ok(response);
    }
}
