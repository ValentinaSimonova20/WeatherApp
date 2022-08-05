package com.simonova.weatherapp.service.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TemperatureInfoBySeason {
    private String season;
    private String year;
    private String maxTemp;
    private String minTemp;
    private String avgTemp;

    public TemperatureInfoBySeason(String season, String year) {
        this.season = season;
        this.year = year;
    }
}
