package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleParkedDTO;
import com.teste.marcossantos.Simulador.de.api.service.VehicleEntryService;
import com.teste.marcossantos.Simulador.de.api.service.VehicleParkedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/webhook/entry")
public class WebhookEntryController {

    @Autowired
    private VehicleEntryService vehicleEntryService;

    @Operation(
            summary = "Receber eventos de entrada em lote",
            description = "Processa uma lista de veículos entrando na garagem. Valida capacidade dos setores e calcula o preço dinâmico com base na ocupação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os veículos processados com sucesso"),
            @ApiResponse(responseCode = "207", description = "Processamento parcial - alguns veículos tiveram erro",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Object.class)))),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<List<Object>> receiveWebhookBatch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de veículos para entrada",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleEntryDTO.class)))
            )
            @RequestBody List<VehicleEntryDTO> dtoList) {
        List<Object> responses = vehicleEntryService.processEntryBatch(dtoList);

        boolean hasError = responses.stream().anyMatch(resp -> ((Map<?, ?>) resp).containsKey("error"));

        HttpStatus status = hasError ? HttpStatus.MULTI_STATUS : HttpStatus.OK;

        return ResponseEntity.status(status).body(responses);
    }
}