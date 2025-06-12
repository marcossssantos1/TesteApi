package com.teste.marcossantos.Simulador.de.api.dto;

public class ParkedResponse {

    private String message;
    private String licensePlate;
    private double lat;
    private double lng;

    public ParkedResponse() {
    }

    public ParkedResponse(String message, String licensePlate, double lat, double lng) {
        this.message = message;
        this.licensePlate = licensePlate;
        this.lat = lat;
        this.lng = lng;
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
