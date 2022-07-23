package com.simonova.weatherapp.requests.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDailyData {
    private List<TemperatureDailyInfo> data;
}
