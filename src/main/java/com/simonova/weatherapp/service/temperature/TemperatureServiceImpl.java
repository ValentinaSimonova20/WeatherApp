package com.simonova.weatherapp.service.temperature;

import com.simonova.weatherapp.requests.weather.GetWeatherClient;
import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;
import com.simonova.weatherapp.service.apiKey.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

    private final GetWeatherClient getWeatherClient;
    private final ApiKeyService apiKeyService;

    private static final String WEATHER_API_KEY_FILE = "weatherApikey.txt";
    private static final String METEOSTAT_HOST = "meteostat.p.rapidapi.com";

    @Override
    public WeatherDailyData getWeatherDailyData(String lat, String lon, String startDate, String endDate) {
        return getWeatherClient.getWeather(
                apiKeyService.getApiKeyFromFile(WEATHER_API_KEY_FILE),
                METEOSTAT_HOST,
                lat,
                lon,
                startDate,
                endDate
        );
    }
}
