package com.simonova.weatherapp.wheatherRequests;

import com.simonova.weatherapp.wheatherRequests.model.ApiSettings;
import com.simonova.weatherapp.wheatherRequests.model.WeatherDailyData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "weather", url = "https://meteostat.p.rapidapi.com")
public interface GetWeatherClient {

    String RAPID_API_HOST = "meteostat.p.rapidapi.com";
    String DAILY_URL = "/point/daily";
    String LAT = "lat";
    String LON = "lon";
    String START_DATE = "start";
    String END_DATE = "end";


    @RequestMapping(
            method = RequestMethod.GET,
            value = DAILY_URL,
            params = {LAT, LON, START_DATE, END_DATE}
    )
    WeatherDailyData getWeather(
            @RequestHeader("X-RapidAPI-Key") String token,
            @RequestHeader("X-RapidAPI-Host") String host,
            @RequestParam(LAT) String lat,
            @RequestParam(LON) String lon,
            @RequestParam(START_DATE) String startDate,
            @RequestParam(END_DATE) String endDate
    );
}
