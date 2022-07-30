package com.simonova.weatherapp.service.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemperatureDailyInfoBySeason {
    private String season;
    private String year;
    private String maxTemp;
    private String minTemp;
    private String avgTemp;

    public TemperatureDailyInfoBySeason(String maxTemp, String minTemp, String avgTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.avgTemp = avgTemp;
    }
}
