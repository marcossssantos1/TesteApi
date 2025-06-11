package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.RevenueRequestDTO;
import com.teste.marcossantos.Simulador.de.api.dto.RevenueResponseDTO;
import com.teste.marcossantos.Simulador.de.api.service.RevenueService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @PostMapping
    public ResponseEntity<RevenueResponseDTO> getRevenue(@RequestBody RevenueRequestDTO dto) {
        RevenueResponseDTO response = revenueService.calculateRevenue(dto.getDate(), dto.getSector());
        return ResponseEntity.ok(response);
    }
}
