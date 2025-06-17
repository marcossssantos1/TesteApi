package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleParkedDTO;
import com.teste.marcossantos.Simulador.de.api.service.VehicleParkedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Receber eventos de estacionamento em lote",
            description = "Processa uma lista de veículos estacionando em vagas específicas da garagem. Valida disponibilidade das vagas e atualiza o status de ocupação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os veículos estacionados com sucesso"),
            @ApiResponse(responseCode = "207", description = "Processamento parcial - alguns veículos tiveram erro",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada ou veículo não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Vaga já ocupada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<List<Object>> receiveWebhookBatch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de veículos com informações de estacionamento",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleParkedDTO.class))
            )
            @RequestBody List<VehicleParkedDTO> dtoList) {
        List<Object> responses = vehicleParkedService.processParkedBatch(dtoList);

        boolean hasError = responses.stream().anyMatch(resp -> ((Map<?, ?>) resp).containsKey("error"));

        HttpStatus status = hasError ? HttpStatus.MULTI_STATUS : HttpStatus.OK;

        return ResponseEntity.status(status).body(responses);
    }
}
