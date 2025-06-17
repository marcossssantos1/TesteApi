package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.RevenueRequestDTO;
import com.teste.marcossantos.Simulador.de.api.dto.RevenueResponseDTO;
import com.teste.marcossantos.Simulador.de.api.service.RevenueService;
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
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @Operation(
            summary = "Consultar receita/faturamento",
            description = "Calcula o faturamento total de um determinado setor em uma data específica. Retorna o valor total e o número de veículos processados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faturamento calculado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RevenueResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição mal formatada (ex: data inválida ou setor inexistente)",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<RevenueResponseDTO> getRevenue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data e setor para cálculo da receita",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RevenueRequestDTO.class))
            )
            @RequestBody RevenueRequestDTO dto) {
        RevenueResponseDTO response = revenueService.calculateRevenue(dto.getDate(), dto.getSector());
        return ResponseEntity.ok(response);
    }
}
