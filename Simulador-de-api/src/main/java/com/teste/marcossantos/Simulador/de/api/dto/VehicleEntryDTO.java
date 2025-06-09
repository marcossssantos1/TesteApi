package com.teste.marcossantos.Simulador.de.api.dto;

import java.time.LocalDateTime;

public class VehicleEntryDTO {

    private String license_plate;
    private LocalDateTime entry_time;

    public VehicleEntryDTO(String license_plate, LocalDateTime entry_time) {
        this.license_plate = license_plate;
        this.entry_time = entry_time;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public LocalDateTime getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(LocalDateTime entry_time) {
        this.entry_time = entry_time;
    }
}
