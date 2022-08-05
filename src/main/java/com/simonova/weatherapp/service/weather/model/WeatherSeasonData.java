package com.simonova.weatherapp.service.weather.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherSeasonData {

    List<TemperatureInfoBySeason> data;
}
