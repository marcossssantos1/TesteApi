package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.PlateStatusResponseDTO;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlateStatusService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public PlateStatusResponseDTO getPlateStatus(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlateAndActiveTrue(licensePlate)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado ou já saiu"));

        LocalDateTime now = LocalDateTime.now();
        double price = vehicle.getPrice();

        return new PlateStatusResponseDTO(
                vehicle.getLicensePlate(),
                price,
                vehicle.getEntryTime(),
                vehicle.getTimeParked(),
                vehicle.getLat(),
                vehicle.getLng()
        );
    }

}
