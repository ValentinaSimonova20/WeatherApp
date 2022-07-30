package com.simonova.weatherapp.service.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherSeasonData {

    List<TemperatureDailyInfoBySeason> data;
}
