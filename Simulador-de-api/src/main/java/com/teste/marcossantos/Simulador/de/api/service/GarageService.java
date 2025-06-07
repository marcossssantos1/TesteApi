package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.GarageResponse;
import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.repository.SectorRepository;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GarageService {

    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private SpotRepository spotRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    public void importGarageData() {
        GarageResponse response = restTemplate.getForObject("http://localhost:3000/garage", GarageResponse.class);

        if (response == null || response.getGarage() == null || response.getSpots() == null) {
            throw new RuntimeException("Resposta inválida do simulador");
        }

        spotRepository.deleteAllInBatch();
        sectorRepository.deleteAllInBatch();

        List<Sector> sectors = response.getGarage().stream().map(dto -> {
            Sector s = new Sector();
            s.setSector(dto.getSector());
            s.setPrice(dto.getBase_price());
            s.setMaxCapacity(dto.getMax_capacity());
            s.setOpenHour(dto.getOpen_hour());
            s.setCloseHour(dto.getClose_hour());
            s.setDurationLimitMinutes(dto.getDuration_limit_minutes());
            return s;
        }).toList();

        List<Sector> savedSectors = sectorRepository.saveAll(sectors);

        Map<String, Sector> sectorMap = savedSectors.stream()
                .collect(Collectors.toMap(Sector::getSector, s -> s));

        List<Spot> spots = response.getSpots().stream().map(dto -> {
            Spot s = new Spot();
            s.setId(dto.getId());
            s.setLat(dto.getLat());
            s.setLng(dto.getLng());
            s.setOccupied(dto.isOccupied());
            s.setSector(sectorMap.get(dto.getSector()));
            return s;
        }).toList();

        spotRepository.saveAll(spots);
    };
}
