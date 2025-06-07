package com.teste.marcossantos.Simulador.de.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SectorDTO {

    private String sector;
    @JsonProperty("base_price")
    private Double base_price;
    @JsonProperty("max_capacity")
    private Integer max_capacity;
    @JsonProperty("open_hour")
    private String open_hour;
    @JsonProperty("close_hour")
    private String close_hour;
    @JsonProperty("duration_limit_minutes")
    private Integer duration_limit_minutes;

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Double getBase_price() {
        return base_price;
    }

    public void setBase_price(Double base_price) {
        this.base_price = base_price;
    }

    public Integer getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(Integer max_capacity) {
        this.max_capacity = max_capacity;
    }

    public String getOpen_hour() {
        return open_hour;
    }

    public void setOpen_hour(String open_hour) {
        this.open_hour = open_hour;
    }

    public String getClose_hour() {
        return close_hour;
    }

    public void setClose_hour(String close_hour) {
        this.close_hour = close_hour;
    }

    public Integer getDuration_limit_minutes() {
        return duration_limit_minutes;
    }

    public void setDuration_limit_minutes(Integer duration_limit_minutes) {
        this.duration_limit_minutes = duration_limit_minutes;
    }
}
