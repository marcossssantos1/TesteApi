package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.dto.GarageResponse;
import com.teste.marcossantos.Simulador.de.api.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/garage")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @Operation(
            summary = "Importar dados da garagem",
            description = "Importa dados para a garagem a partir de uma fonte configurada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados importados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao importar dados")
    })
    @PostMapping
    public ResponseEntity<String> importData() {
        garageService.importGarageData();
        return ResponseEntity.ok("Dados importados com sucesso");
    }
}
