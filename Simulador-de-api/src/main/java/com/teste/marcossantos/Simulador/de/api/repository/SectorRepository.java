package com.teste.marcossantos.Simulador.de.api.repository;

import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findBySector(String name);
}
