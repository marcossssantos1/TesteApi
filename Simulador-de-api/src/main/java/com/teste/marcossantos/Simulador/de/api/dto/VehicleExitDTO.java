package com.teste.marcossantos.Simulador.de.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class VehicleExitDTO {

    private String event_type;
    private String licensePlate;
    private LocalDateTime exitTime;

    public VehicleExitDTO() {
    }

    public VehicleExitDTO(String event_type, String licensePlate, LocalDateTime exitTime) {
        this.event_type = event_type;
        this.licensePlate = licensePlate;
        this.exitTime = exitTime;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

}
