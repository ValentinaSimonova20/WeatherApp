package com.simonova.weatherapp.wheatherRequests.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDailyData {
    private List<TemperatureDailyInfo> data;
}
