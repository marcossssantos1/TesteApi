package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WebhookService {

    @Autowired
    private VehicleEntryService vehicleEntryService;
    @Autowired
    private VehicleParkedService vehicleParkedService;
    @Autowired
    private VehicleExitService vehicleExitService;

    public List<Object> processWebhookBatch(List<WebhookDTO> dtoList) {
        List<Object> responses = new ArrayList<>();

        for (WebhookDTO dto : dtoList) {
            try {
                if (dto.getEvent_type() == null) {
                    responses.add(Map.of(
                            "status", 400,
                            "error", "Evento sem tipo especificado",
                            "license_plate", dto.getLicense_plate()
                    ));
                    continue;
                }

                String eventType = dto.getEvent_type().toUpperCase();

                switch (eventType) {
                    case "ENTRY" -> {
                        VehicleEntryDTO entryDTO = new VehicleEntryDTO(dto.getLicense_plate(), dto.getEntry_time());
                        vehicleEntryService.processEntry(entryDTO);
                        responses.add(Map.of(
                                "status", 200,
                                "message", "Entrada registrada com sucesso",
                                "license_plate", dto.getLicense_plate()
                        ));
                    }
                    case "PARKED" -> {
                        vehicleParkedService.handleParked(dto.getLicense_plate(), dto.getLat(), dto.getLng());
                        responses.add(Map.of(
                                "status", 200,
                                "message", "Veículo estacionado com sucesso",
                                "license_plate", dto.getLicense_plate()
                        ));
                    }
                    case "EXIT" -> {
                        vehicleExitService.processExit(dto.getLicense_plate(), dto.getExitTime());
                        responses.add(Map.of(
                                "status", 200,
                                "message", "Saída registrada com sucesso",
                                "license_plate", dto.getLicense_plate()
                        ));
                    }
                    default -> {
                        responses.add(Map.of(
                                "status", 400,
                                "error", "Evento não suportado",
                                "event_type", dto.getEvent_type()
                        ));
                    }
                }
            } catch (Exception e) {
                responses.add(Map.of(
                        "status", 500,
                        "error", "Erro ao processar evento",
                        "message", e.getMessage(),
                        "license_plate", dto.getLicense_plate()
                ));
            }
        }

        return responses;
    }
}