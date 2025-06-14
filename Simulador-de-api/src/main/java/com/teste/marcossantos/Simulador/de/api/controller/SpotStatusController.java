package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.SpotDTO;
import com.teste.marcossantos.Simulador.de.api.service.SpotStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/spot-status")
public class SpotStatusController {

    @Autowired
    private SpotStatusService spotStatusService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> checkSpotStatus(@RequestBody SpotDTO dto) {
        Map<String, Object> response = spotStatusService.getSpotStatus(dto.getLat(), dto.getLng());
        return ResponseEntity.ok(response);
    }
}
