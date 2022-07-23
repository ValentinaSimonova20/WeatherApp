package com.simonova.weatherapp.service.weather;

import com.simonova.weatherapp.controllers.model.WeatherRequest;
import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;

public interface WeatherService {
    WeatherDailyData getWeatherDailyData(WeatherRequest weatherRequest);
}
