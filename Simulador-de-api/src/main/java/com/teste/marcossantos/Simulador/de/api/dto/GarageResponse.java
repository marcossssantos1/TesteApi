package com.teste.marcossantos.Simulador.de.api.dto;

import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;

import java.util.List;


public class GarageResponse {
    private List<SectorDTO> garage;
    private List<SpotDTO> spots;

    public GarageResponse() {
    }

    public GarageResponse(List<SectorDTO> garage, List<SpotDTO> spots) {
        this.garage = garage;
        this.spots = spots;
    }

    public List<SectorDTO> getGarage() {
        return garage;
    }

    public void setGarage(List<SectorDTO> garage) {
        this.garage = garage;
    }

    public List<SpotDTO> getSpots() {
        return spots;
    }

    public void setSpots(List<SpotDTO> spots) {
        this.spots = spots;
    }
}