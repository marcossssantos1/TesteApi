package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleEventDTO;
import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.repository.SectorRepository;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private SpotRepository spotRepository;

    public void processEntry(VehicleEventDTO dto) {
        // Verifica se o veículo já está ativo
        if (vehicleRepository.findByLicensePlateAndActiveTrue(dto.getLicense_plate()).isPresent()) {
            System.out.println("Veículo já está dentro da garagem!");
            return;
        }

        // Buscar todos os setores
        List<Sector> sectors = sectorRepository.findAll();

        Sector chosenSector = null;
        double finalPrice = 0.0;

        for (Sector sector : sectors) {
            List<Spot> spots = spotRepository.findBySector_Sector(sector.getSector());

            long occupiedCount = spots.stream().filter(Spot::getOccupied).count();
            if (occupiedCount >= sector.getMaxCapacity()) {
                continue; // Pular setores lotados
            }

            double occupancyRate = (double) occupiedCount / sector.getMaxCapacity();
            double price = sector.getPrice();

            if (occupancyRate < 0.25) {
                price *= 0.90;
            } else if (occupancyRate < 0.50) {
                price *= 1.0;
            } else if (occupancyRate < 0.75) {
                price *= 1.10;
            } else {
                price *= 1.25;
            }

            // Escolhe o setor com menor taxa de ocupação ou primeiro disponível
            if (chosenSector == null || occupancyRate <
                    ((double) spotRepository.findBySector_Sector(chosenSector.getSector())
                            .stream().filter(Spot::getOccupied).count() / chosenSector.getMaxCapacity())) {
                chosenSector = sector;
                finalPrice = price;
            }
        }

        if (chosenSector == null) {
            System.out.println("Todos os setores estão lotados!");
            return;
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

        if (spot.getOccupied()) {
            throw new RuntimeException("Vaga já ocupada!");
        }

        Vehicle entry = vehicleRepository.findByLicensePlateAndExitTimeIsNull(licensePlate)
                .orElseThrow(() -> new RuntimeException("Entrada não encontrada para a placa"));

        spot.setOccupied(true);
        spotRepository.save(spot); // salvar atualização da vaga

        entry.setLat(lat);
        entry.setLng(lng);
        entry.setTimeParked(LocalDateTime.now());
        vehicleRepository.save(entry);
    }


}
