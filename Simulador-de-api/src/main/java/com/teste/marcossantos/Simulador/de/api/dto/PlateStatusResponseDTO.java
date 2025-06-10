package com.teste.marcossantos.Simulador.de.api.dto;

import java.time.LocalDateTime;

public class PlateStatusResponseDTO {

    private String license_plate;
    private double price_until_now;
    private LocalDateTime entry_time;
    private LocalDateTime time_parked;
    private double lat;
    private double lng;

    public PlateStatusResponseDTO(String license_plate, double price_until_now, LocalDateTime entry_time, LocalDateTime time_parked, double lat, double lng) {
        this.license_plate = license_plate;
        this.price_until_now = price_until_now;
        this.entry_time = entry_time;
        this.time_parked = time_parked;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public double getPrice_until_now() {
        return price_until_now;
    }

    public void setPrice_until_now(double price_until_now) {
        this.price_until_now = price_until_now;
    }

    public LocalDateTime getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(LocalDateTime entry_time) {
        this.entry_time = entry_time;
    }

    public LocalDateTime getTime_parked() {
        return time_parked;
    }

    public void setTime_parked(LocalDateTime time_parked) {
        this.time_parked = time_parked;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
