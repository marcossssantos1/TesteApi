package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.ParkedResponse;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.exceptions.SpotOccupiedException;
import com.teste.marcossantos.Simulador.de.api.exceptions.VehicleNotFoundException;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VehicleParkedService {

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public void handleParked(String licensePlate, double lat, double lng) {
        Spot spot = spotRepository.findByLatAndLng(lat, lng);

        if (spot == null) {
            throw new RuntimeException("Vaga não encontrada com essas coordenadas");
        }

        if (spot.getOccupied()) {
            throw new SpotOccupiedException("Vaga já está ocupada!");
        }

        Vehicle entry = vehicleRepository.findByLicensePlateAndExitTimeIsNull(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundException("Veículo com placa " + licensePlate + " não encontrado ou já saiu"));

        spot.setOccupied(true);
        spotRepository.save(spot);

        entry.setLat(lat);
        entry.setLng(lng);
        entry.setTimeParked(LocalDateTime.now());
        vehicleRepository.save(entry);

    }

}
