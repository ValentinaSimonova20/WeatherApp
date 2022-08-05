package com.simonova.weatherapp.service.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeasonYearModel {
    private String season;
    private String year;
}
