package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.RevenueResponseDTO;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RevenueService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public RevenueResponseDTO calculateRevenue(String dateStr, String sector) {
        LocalDate date = LocalDate.parse(dateStr);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Vehicle> vehicles = vehicleRepository.findBySectorAndExitTimeBetween(sector, start, end);

        double total = vehicles.stream()
                .mapToDouble(Vehicle::getPrice)
                .sum();

        return new RevenueResponseDTO(total, start.toString());
    }

}
