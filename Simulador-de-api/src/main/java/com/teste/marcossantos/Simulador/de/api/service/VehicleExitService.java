package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleExitResponse;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.exceptions.VehicleNotFoundException;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VehicleExitService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private SpotRepository spotRepository;

    public VehicleExitResponse processExit(String licensePlate, LocalDateTime exitTime) {
        Vehicle vehicle = vehicleRepository.findByLicensePlateAndExitTimeIsNull(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundException("Veículo com placa " + licensePlate + " não encontrado ou já saiu"));

        Spot spot = spotRepository.findByLatAndLng(vehicle.getLat(), vehicle.getLng());
        if (spot != null) {
            spot.setOccupied(false);
            spotRepository.save(spot);
        }

        vehicle.setExitTime(exitTime);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);

        return buildExitResponse(vehicle);
    }

    private VehicleExitResponse buildExitResponse(Vehicle vehicle) {
        return new VehicleExitResponse(
                "Saída registrada com sucesso.",
                vehicle.getLicensePlate(),
                vehicle.getExitTime(),
                vehicle.getSector(),
                vehicle.getPrice()
        );
    }

}
