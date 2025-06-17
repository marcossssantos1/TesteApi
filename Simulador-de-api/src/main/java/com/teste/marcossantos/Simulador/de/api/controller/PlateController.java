package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.PlateStatusResponseDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.service.PlateStatusService;
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

@RestController
@RequestMapping("/plate-status")
public class PlateController {

    @Autowired
    private PlateStatusService plateStatusService;

    @Operation(
            summary = "Consulta status da placa do veículo",
            description = "Retorna o status atual do veículo com base na placa informada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da placa obtido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlateStatusResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada (placa inválida ou faltando)",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado para a placa informada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<PlateStatusResponseDTO> getPlateStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO contendo a placa do veículo",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleEntryDTO.class))
            )
            @RequestBody VehicleEntryDTO dto) {
        PlateStatusResponseDTO status = plateStatusService.getPlateStatus(dto.getLicense_plate());
        return ResponseEntity.ok(status);
    }
}
