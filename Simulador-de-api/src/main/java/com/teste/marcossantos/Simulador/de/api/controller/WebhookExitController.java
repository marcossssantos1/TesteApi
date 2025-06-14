package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleExitDTO;
import com.teste.marcossantos.Simulador.de.api.service.VehicleEntryService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleExitService;
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
@RequestMapping("/webhook/exit")
public class WebhookExitController {

    @Autowired
    private VehicleExitService vehicleExitService;

    @PostMapping
    public ResponseEntity<List<Object>> receiveWebhookBatch(@RequestBody List<VehicleExitDTO> dtoList) {
        List<Object> responses = vehicleExitService.processExitBatch(dtoList);

        boolean hasError = responses.stream().anyMatch(resp -> ((Map<?, ?>) resp).containsKey("error"));

        HttpStatus status = hasError ? HttpStatus.MULTI_STATUS : HttpStatus.OK;

        return ResponseEntity.status(status).body(responses);
    }
}