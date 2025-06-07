package com.teste.marcossantos.Simulador.de.api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "sectors")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sector;
    private Double price;
    private int maxCapacity;
    private String openHour;
    private String closeHour;
    private int durationLimitMinutes;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<Spot> spots;

    public Sector() {
    }

    public Sector(Long id, String sector, Double price, int maxCapacity, String openHour, String closeHour, int durationLimitMinutes) {
        this.id = id;
        this.sector = sector;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.durationLimitMinutes = durationLimitMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getOpenHour() {
        return openHour;
    }


    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public int getDurationLimitMinutes() {
        return durationLimitMinutes;
    }

    public void setDurationLimitMinutes(int durationLimitMinutes) {
        this.durationLimitMinutes = durationLimitMinutes;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}
