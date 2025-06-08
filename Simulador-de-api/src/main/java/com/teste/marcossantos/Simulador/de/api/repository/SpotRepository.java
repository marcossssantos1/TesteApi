package com.teste.marcossantos.Simulador.de.api.repository;

import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findBySector_Sector(String sector);
    Spot findByLatAndLng(double lat, double lng);
}
