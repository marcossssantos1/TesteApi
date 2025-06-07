package com.teste.marcossantos.Simulador.de.api.entity;

import jakarta.persistence.*;

@Entity
@Table()
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private String sector;
    private Double lat;
    private Double lng;
    private Boolean occupied;

    public Spot() {
    }

    public Spot(Long id, String sector, Double lat, Double lng, Boolean occupied) {
        this.id = id;
        this.sector = sector;
        this.lat = lat;
        this.lng = lng;
        this.occupied = occupied;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
