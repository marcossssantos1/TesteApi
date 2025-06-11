package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SpotStatusService {

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Map<String, Object> getSpotStatus(double lat, double lng) {
        Map<String, Object> response = new HashMap<>();

        Spot spot = spotRepository.findByLatAndLng(lat, lng);
        if (spot == null) {
            throw new RuntimeException("Vaga não encontrada.");
        }

        response.put("Ocupada", spot.getOccupied());

        if (spot.getOccupied()) {
            Optional<Vehicle> vehicleOpt = vehicleRepository.findByLatAndLngAndExitTimeIsNull(lat, lng);
            if (vehicleOpt.isPresent()) {
                Vehicle vehicle = vehicleOpt.get();
                response.put("license_plate", vehicle.getLicensePlate());
                response.put("entry_time", vehicle.getEntryTime());
                response.put("time_parked", vehicle.getTimeParked());
                response.put("price_until_now", vehicle.getPrice());
            } else {
                preencherVazio(response);
            }
        } else {
            preencherVazio(response);
        }

        return response;
    }

    private void preencherVazio(Map<String, Object> response) {
        response.put("license_plate", "");
        response.put("price_until_now", 0.0);
        response.put("entry_time", null);
        response.put("time_parked", null);
    }

}
