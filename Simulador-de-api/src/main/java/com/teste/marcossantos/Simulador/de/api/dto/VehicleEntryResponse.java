package com.teste.marcossantos.Simulador.de.api.dto;

public class VehicleEntryResponse {

    private String licensePlate;
    private String sector;
    private String message;

    public VehicleEntryResponse() {}

    public VehicleEntryResponse(String licensePlate, String sector, String message) {
        this.licensePlate = licensePlate;
        this.sector = sector;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
