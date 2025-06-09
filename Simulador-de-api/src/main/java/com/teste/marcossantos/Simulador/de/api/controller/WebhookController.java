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
    public ResponseEntity<String> receiveEvent(@RequestBody WebhookDTO dto) {
        if (dto.getEvent_type() == null) {
            return ResponseEntity.badRequest().body("Campo event_type é obrigatório");
        }

        switch(dto.getEvent_type().toUpperCase()) {
            case "ENTRY":
                if (dto.getLicense_plate() == null || dto.getEntry_time() == null) {
                    return ResponseEntity.badRequest().body("license_plate e entry_time são obrigatórios para ENTRY");
                }
                VehicleEntryDTO entryDTO = new VehicleEntryDTO(dto.getLicense_plate(), dto.getEntry_time());
                vehicleService.processEntry(entryDTO);
                break;

            case "PARKED":
                if (dto.getLicense_plate() == null || dto.getLat() == null || dto.getLng() == null) {
                    return ResponseEntity.badRequest().body("license_plate, lat e lng são obrigatórios para PARKED");
                }
                VehicleParkedDTO parkedDTO = new VehicleParkedDTO(dto.getLicense_plate(), dto.getLat(), dto.getLng());
                vehicleService.handleParked(parkedDTO.getLicense_plate(), parkedDTO.getLat(), parkedDTO.getLng());
                break;
            case "EXIT":
                vehicleService.processExit(dto.getLicense_plate(), dto.getExitTime());
                break;
            default:
                return ResponseEntity.badRequest().body("Evento não suportado");
        }
        return ResponseEntity.ok("Evento processado com sucesso");
    }
}
