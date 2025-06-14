package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleParkedDTO;
import com.teste.marcossantos.Simulador.de.api.dto.WebhookDTO;
import com.teste.marcossantos.Simulador.de.api.service.VehicleParkedService;
import com.teste.marcossantos.Simulador.de.api.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webhook/parked")
public class WebhookParkedController {

    @Autowired
    private VehicleParkedService vehicleParkedService;

    @PostMapping
    public ResponseEntity<List<Object>> receiveWebhookBatch(@RequestBody List<VehicleParkedDTO> dtoList) {
        List<Object> responses = vehicleParkedService.processParkedBatch(dtoList);

        boolean hasError = responses.stream().anyMatch(resp -> ((Map<?, ?>) resp).containsKey("error"));

        HttpStatus status = hasError ? HttpStatus.MULTI_STATUS : HttpStatus.OK;

        return ResponseEntity.status(status).body(responses);
    }
}
