package com.teste.marcossantos.Simulador.de.api.dto;

public class RevenueResponseDTO {

    private double amount;
    private String currency;
    private String timestamp;

    public RevenueResponseDTO(double amount, String timestamp) {
        this.amount = amount;
        this.currency = "BRL";
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
