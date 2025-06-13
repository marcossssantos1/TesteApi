package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.ParkedResponse;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryResponse;
import com.teste.marcossantos.Simulador.de.api.dto.WebhookDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleExitResponse;
import com.teste.marcossantos.Simulador.de.api.service.VehicleExitService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleParkedService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleEntryService;
import com.teste.marcossantos.Simulador.de.api.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private WebhookService webhookService;

    @PostMapping
    public ResponseEntity<?> receiveWebhookBatch(@RequestBody List<WebhookDTO> dtoList) {
        List<Object> responses = webhookService.processWebhookBatch(dtoList);
        return ResponseEntity.ok(responses);
    }
}
