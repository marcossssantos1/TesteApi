package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.PlateStatusResponseDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.service.PlateStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plate-status")
public class PlateController {

    @Autowired
    private PlateStatusService plateStatusService;

    @PostMapping
    public ResponseEntity<PlateStatusResponseDTO> getPlateStatus(@RequestBody VehicleEntryDTO dto) {
        PlateStatusResponseDTO status = plateStatusService.getPlateStatus(dto.getLicense_plate());
        return ResponseEntity.ok(status);
    }
}
