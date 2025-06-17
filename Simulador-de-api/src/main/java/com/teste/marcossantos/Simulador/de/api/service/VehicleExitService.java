package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleExitDTO;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.exceptions.VehicleNotFoundException;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VehicleExitService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private SpotRepository spotRepository;

    public void processExit(String licensePlate, LocalDateTime exitTime) {
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

    }

    public List<Object> processExitBatch(List<VehicleExitDTO> dtoList) {
        List<Object> responses = new ArrayList<>();

        for (VehicleExitDTO dto : dtoList) {
            try {
                Vehicle vehicle = vehicleRepository.findByLicensePlateAndExitTimeIsNull(dto.getLicensePlate())
                        .orElseThrow(() -> new VehicleNotFoundException("Veículo com placa " + dto.getLicensePlate() + " não encontrado ou já saiu"));

                // Libera a vaga
                Spot spot = spotRepository.findByLatAndLng(vehicle.getLat(), vehicle.getLng());
                if (spot != null) {
                    spot.setOccupied(false);
                    spotRepository.save(spot);
                }

                vehicle.setExitTime(dto.getExitTime());
                vehicle.setActive(false);
                vehicleRepository.save(vehicle);

                // Adiciona à resposta com valor
                responses.add(Map.of(
                        "license_plate", dto.getLicensePlate(),
                        "status", "Saída registrada com sucesso",
                        "price", vehicle.getPrice()
                ));

            } catch (VehicleNotFoundException e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicensePlate(),
                        "error", "Veículo não encontrado ou já saiu",
                        "statusCode", HttpStatus.NOT_FOUND.value()
                ));
            } catch (Exception e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicensePlate(),
                        "error", "Erro interno: " + e.getMessage(),
                        "statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
            }
        }

        return responses;
    }

}
