package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.SpotDTO;
import com.teste.marcossantos.Simulador.de.api.service.SpotStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Verificar status de uma vaga",
            description = "Consulta o status de ocupação de uma vaga específica com base em suas coordenadas (latitude e longitude)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da vaga retornado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> checkSpotStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Coordenadas da vaga (latitude e longitude)",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpotDTO.class))
            )
            @RequestBody SpotDTO dto) {
        Map<String, Object> response = spotStatusService.getSpotStatus(dto.getLat(), dto.getLng());
        return ResponseEntity.ok(response);
    }
}
