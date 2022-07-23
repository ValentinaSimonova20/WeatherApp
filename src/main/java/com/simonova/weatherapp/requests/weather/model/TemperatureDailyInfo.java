package com.simonova.weatherapp.requests.weather.model;

import lombok.Data;

@Data
public class TemperatureDailyInfo {
    private String date;
    private String tavg;
    private String tmin;
    private String tmax;

}
