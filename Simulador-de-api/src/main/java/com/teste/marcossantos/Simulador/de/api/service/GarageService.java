package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.GarageResponse;
import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.repository.SectorRepository;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GarageService {

    @Autowired
    private final SectorRepository sectorRepository;
    @Autowired
    private final SpotRepository spotRepository;

    public GarageService(SectorRepository sectorRepository, SpotRepository spotRepository) {
        this.sectorRepository = sectorRepository;
        this.spotRepository = spotRepository;
    }

    public void saveGarageData(GarageResponse response) {
        // Salva os setores e mapeia pelo nome (ex: "A")
        List<Sector> savedSectors = sectorRepository.saveAll(response.getGarage());
        Map<String, Sector> sectorMap = savedSectors.stream()
                .collect(Collectors.toMap(Sector::getSector, Function.identity()));

        // Transforma cada spot e relaciona com o objeto Sector
        List<Spot> spotsToSave = response.getSpots().stream().map(spotDto -> {
            Spot spot = new Spot();
            spot.setId(spotDto.getId());
            spot.setLat(spotDto.getLat());
            spot.setLng(spotDto.getLng());
            spot.setOccupied(spotDto.getOccupied());
            spot.setSector(spotDto.getSector()); // relacionamento real
            return spot;
        }).collect(Collectors.toList());

        spotRepository.saveAll(spotsToSave);
    }
}
