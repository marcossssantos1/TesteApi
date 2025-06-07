package com.teste.marcossantos.Simulador.de.api.dto;

import com.teste.marcossantos.Simulador.de.api.entity.Sector;
import com.teste.marcossantos.Simulador.de.api.entity.Spot;

import java.util.List;


public class GarageResponse {
    private List<Sector> garage;
    private List<Spot> spots;

    public GarageResponse() {
    }

    public GarageResponse(List<Sector> garage, List<Spot> spots) {
        this.garage = garage;
        this.spots = spots;
    }

    public List<Sector> getGarage() {
        return garage;
    }

    public void setGarage(List<Sector> garage) {
        this.garage = garage;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}