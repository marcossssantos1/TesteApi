package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryDTO;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleEntryResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VehicleEntryService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private SpotRepository spotRepository;

    public void  processEntry(VehicleEntryDTO dto) {
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

        double precoOriginal = finalPrice;
        double precoArredondado = Math.round(precoOriginal * 100.0) / 100.0;


        vehicle.setLicensePlate(dto.getLicense_plate());
        vehicle.setEntryTime(dto.getEntry_time());
        vehicle.setSector(chosenSector.getSector());
        vehicle.setPrice(precoArredondado);
        vehicle.setActive(true);

        vehicleRepository.save(vehicle);


    }

    public List<Object> processEntryBatch(List<VehicleEntryDTO> dtoList) {
        List<Object> responses = new ArrayList<>();

        for (VehicleEntryDTO dto : dtoList) {
            try {
                processEntry(dto);  // Sua lógica já existente de entrada
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "status", "Entrada registrada com sucesso"
                ));
            } catch (SectorFullException e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "error", "Setor está cheio!",
                        "statusCode", HttpStatus.BAD_REQUEST.value()
                ));
            } catch (SpotOccupiedException e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "error", "Vaga já está ocupada!",
                        "statusCode", HttpStatus.CONFLICT.value()
                ));
            } catch (Exception e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "error", "Erro interno: " + e.getMessage(),
                        "statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
            }
        }

        return responses;
    }

}
