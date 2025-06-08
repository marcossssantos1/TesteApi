package com.teste.marcossantos.Simulador.de.api.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public class VehicleEventDTO {

    private String license_plate;
    private LocalDateTime entry_time;
    private String event_type;

    public VehicleEventDTO(String license_plate, LocalDateTime entry_time) {
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

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }
}
