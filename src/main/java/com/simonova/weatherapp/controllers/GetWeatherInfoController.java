package com.simonova.weatherapp.controllers;

import com.simonova.weatherapp.controllers.model.WeatherRequest;
import com.simonova.weatherapp.service.apiKey.ApiKeyService;
import com.simonova.weatherapp.requests.weather.GetWeatherClient;
import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;
import com.simonova.weatherapp.service.weather.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GetWeatherInfoController {

    private WeatherService weatherService;

    @GetMapping("/getTemps")
    public @ResponseBody WeatherDailyData getWeatherInfo(WeatherRequest weatherRequest) {
        return weatherService.getWeatherDailyData(weatherRequest);
    }
}
