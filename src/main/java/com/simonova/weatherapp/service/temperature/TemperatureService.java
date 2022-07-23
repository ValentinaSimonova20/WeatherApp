package com.simonova.weatherapp.service.temperature;

import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;
import org.springframework.web.bind.annotation.RequestParam;

public interface TemperatureService {
    WeatherDailyData getWeatherDailyData(String lat, String lon, String startDate, String endDate);
}
