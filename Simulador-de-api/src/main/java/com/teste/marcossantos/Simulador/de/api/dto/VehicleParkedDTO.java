package com.teste.marcossantos.Simulador.de.api.dto;

public class VehicleParkedDTO {

    private String license_plate;
    private Double lat;
    private Double lng;

    public VehicleParkedDTO(String license_plate, Double lat, Double lng) {
        this.license_plate = license_plate;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
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
}
