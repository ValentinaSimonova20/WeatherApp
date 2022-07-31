package com.simonova.weatherapp.service.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class TemperatureInfoBySeason {
    private String season;
    private String year;
    private String maxTemp;
    private String minTemp;
    private String avgTemp;

    public TemperatureInfoBySeason(String maxTemp, String minTemp, String avgTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.avgTemp = avgTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureInfoBySeason that = (TemperatureInfoBySeason) o;
        return Objects.equals(season, that.season) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, year);
    }
}
