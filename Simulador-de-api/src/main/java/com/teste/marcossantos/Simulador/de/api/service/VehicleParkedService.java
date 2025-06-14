package com.teste.marcossantos.Simulador.de.api.service;

import com.teste.marcossantos.Simulador.de.api.dto.ParkedResponse;
import com.teste.marcossantos.Simulador.de.api.dto.VehicleParkedDTO;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import com.teste.marcossantos.Simulador.de.api.exceptions.SpotNotFoundException;
import com.teste.marcossantos.Simulador.de.api.exceptions.SpotOccupiedException;
import com.teste.marcossantos.Simulador.de.api.exceptions.VehicleNotFoundException;
import com.teste.marcossantos.Simulador.de.api.repository.SpotRepository;
import com.teste.marcossantos.Simulador.de.api.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            throw new SpotNotFoundException("Vaga não encontrada com essas coordenadas");
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

    @Transactional
    public List<Object> processParkedBatch(List<VehicleParkedDTO> dtoList) {
        List<Object> responses = new ArrayList<>();

        for (VehicleParkedDTO dto : dtoList) {
            try {
                handleParked(dto.getLicense_plate(), dto.getLat(), dto.getLng());
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "status", "Sucesso ao estacionar"
                ));
            } catch (SpotOccupiedException e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "error", "Vaga já está ocupada!",
                        "statusCode", HttpStatus.CONFLICT.value()
                ));
            }catch (SpotNotFoundException e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "error", "Vaga não encontrada com essas cordenadas.",
                        "statusCode", HttpStatus.NOT_FOUND.value()
                ));
            }
            catch (VehicleNotFoundException e) {
                responses.add(Map.of(
                        "license_plate", dto.getLicense_plate(),
                        "error", "Veículo não encontrado ou já saiu!",
                        "statusCode", HttpStatus.NOT_FOUND.value()
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
