package com.teste.marcossantos.Simulador.de.api.controller;

import com.teste.marcossantos.Simulador.de.api.service.ExternalApisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExternalApisController {

    @Autowired
    private ExternalApisService service;

    @GetMapping
    public ResponseEntity<String> get(){
        String all = service.findAll();

        return ResponseEntity.ok(all);
    }
}
