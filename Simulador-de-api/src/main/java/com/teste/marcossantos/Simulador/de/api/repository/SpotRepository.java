package com.teste.marcossantos.Simulador.de.api.repository;

import com.teste.marcossantos.Simulador.de.api.entity.Spot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findBySector_Sector(String sector);
    Spot findByLatAndLng(double lat, double lng);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Spot s WHERE s.lat = :lat AND s.lng = :lng")
    Spot findByLatAndLngForUpdate(@Param("lat") double lat, @Param("lng") double lng);
}
