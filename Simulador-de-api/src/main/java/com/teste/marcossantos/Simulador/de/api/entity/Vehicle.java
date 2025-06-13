package com.teste.marcossantos.Simulador.de.api.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;

    private String sector;

    private Double price;

    private boolean active;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Double lat;

    private Double lng;

    private LocalDateTime timeParked;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sectors;

    public Vehicle() {
    }

    public Vehicle(Long id, String licensePlate, String sector, Double price, boolean active, LocalDateTime entryTime, LocalDateTime exitTime, Double lat, Double lng, LocalDateTime timeParked, Sector sectors) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.sector = sector;
        this.price = price;
        this.active = active;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.lat = lat;
        this.lng = lng;
        this.timeParked = timeParked;
        this.sectors = sectors;
    }

    public Vehicle(Long id, String licensePlate, String sector, Double price, boolean active, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.sector = sector;
        this.price = price;
        this.active = active;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
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

    public Sector getSectors() {
        return sectors;
    }

    public void setSectors(Sector sectors) {
        this.sectors = sectors;
    }

    public LocalDateTime getTimeParked() {
        return timeParked;
    }

    public void setTimeParked(LocalDateTime timeParked) {
        this.timeParked = timeParked;
    }
}
