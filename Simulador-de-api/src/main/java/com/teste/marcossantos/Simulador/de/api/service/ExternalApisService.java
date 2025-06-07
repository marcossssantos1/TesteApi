package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApisService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private SpotRepository repository;

    public String findAll(){
        String url = "http://localhost:3000/garage";
        ResponseEntity<String> forEntity = template.getForEntity(url, String.class);

        return  forEntity.getBody();
    }
}
