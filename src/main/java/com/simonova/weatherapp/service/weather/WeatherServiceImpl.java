package com.simonova.weatherapp.service.weather;

import com.simonova.weatherapp.controllers.model.WeatherRequest;
import com.simonova.weatherapp.requests.location.model.LocationInfo;
import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;
import com.simonova.weatherapp.service.coordinates.CoordinatesService;
import com.simonova.weatherapp.service.temperature.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final CoordinatesService coordinatesService;
    private final TemperatureService temperatureService;

    @Override
    public WeatherDailyData getWeatherDailyData(WeatherRequest weatherRequest) {
        LocationInfo locationInfo = coordinatesService.getCoordinatesByAddress(weatherRequest.getCity());
        return temperatureService.getWeatherDailyData(
                locationInfo.getLatitude(),
                locationInfo.getLongitude(),
                weatherRequest.getStartDate(),
                weatherRequest.getEndDate()
        );
    }
}
