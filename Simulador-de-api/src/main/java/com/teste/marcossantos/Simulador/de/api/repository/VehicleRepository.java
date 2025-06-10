package com.teste.marcossantos.Simulador.de.api.repository;

import com.teste.marcossantos.Simulador.de.api.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicensePlateAndActiveTrue(String licensePlate);

     Optional<Vehicle> findByLicensePlateAndExitTimeIsNull(String licensePlate);

    Optional<Vehicle> findByLatAndLngAndExitTimeIsNull(double lat, double lng);

    List<Vehicle> findBySectorAndExitTimeBetween(String sector, LocalDateTime start, LocalDateTime end);

}
