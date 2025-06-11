package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.PlateStatusResponseDTO;
import com.teste.marcossantos.Simulador.de.api.dto.RevenueResponseDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.exceptions.SectorFullException;
import com.teste.marcossantos.Simulador.de.api.exceptions.SpotOccupiedException;
import com.teste.marcossantos.Simulador.de.api.exceptions.VehicleNotFoundException;
import com.teste.marcossantos.Simulador.de.api.repository.SectorRepository;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private SpotRepository spotRepository;

    public void processEntry(VehicleEntryDTO dto) {
        if (vehicleRepository.findByLicensePlateAndActiveTrue(dto.getLicense_plate()).isPresent()) {
            throw new RuntimeException("Veículo já está dentro da garagem!");
        }

        List<Sector> sectors = sectorRepository.findAll();

        Sector chosenSector = null;
        double finalPrice = 0.0;

        for (Sector sector : sectors) {
            List<Spot> spots = spotRepository.findBySector_Sector(sector.getSector());

            long occupiedCount = spots.stream().filter(Spot::getOccupied).count();
            if (occupiedCount >= sector.getMaxCapacity()) {
                continue;
            }

            double occupancyRate = (double) occupiedCount / sector.getMaxCapacity();
            double price = sector.getPrice();

            if (occupancyRate < 0.25) {
                price -= price * 0.10;
            } else if (occupancyRate < 0.50) {
                price = price;
            } else if (occupancyRate < 0.75) {
                price *= 1.10;
            } else {
                price *= 1.25;
            }

            if (chosenSector == null || occupancyRate <
                    ((double) spotRepository.findBySector_Sector(chosenSector.getSector())
                            .stream().filter(Spot::getOccupied).count() / chosenSector.getMaxCapacity())) {
                chosenSector = sector;
                finalPrice = price;
            }
        }

        if (chosenSector == null) {
            throw new SectorFullException("Todos os setores estão lotados!");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(dto.getLicense_plate());
        vehicle.setEntryTime(dto.getEntry_time());
        vehicle.setSector(chosenSector.getSector());
        vehicle.setPrice(finalPrice);
        vehicle.setActive(true);

        vehicleRepository.save(vehicle);

        System.out.println("Veículo " + vehicle.getLicensePlate() +
                " entrou no setor " + chosenSector.getSector() +
                " com preço calculado de R$ " + finalPrice);
    }

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

        System.out.println("Saída registrada: " + licensePlate + " liberou a vaga.");
    }
}
