package com.simonova.weatherapp.wheatherRequests.model;

import lombok.Data;

@Data
public class TemperatureDailyInfo {
    private String date;
    private String tavg;
    private String tmin;
    private String tmax;

}
