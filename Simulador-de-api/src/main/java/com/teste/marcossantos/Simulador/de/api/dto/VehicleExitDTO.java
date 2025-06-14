package com.teste.marcossantos.Simulador.de.api.dto;

import java.time.LocalDateTime;

public class VehicleExitDTO {

    private String message;
    private String licensePlate;
    private LocalDateTime exitTime;
    private double finalPrice;

    public VehicleExitDTO() {
    }

    public VehicleExitDTO(String message, String licensePlate, LocalDateTime exitTime, double finalPrice) {
        this.message = message;
        this.licensePlate = licensePlate;
        this.exitTime = exitTime;
        this.finalPrice = finalPrice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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


    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
