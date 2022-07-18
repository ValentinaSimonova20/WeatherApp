package com.simonova.weatherapp.controllers;

import com.simonova.weatherapp.wheatherRequests.GetWeatherClient;
import com.simonova.weatherapp.wheatherRequests.model.WeatherDailyData;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class GetWeatherInfoController {

    private GetWeatherClient getWeatherClient;


    @GetMapping("/getTemps")
    public @ResponseBody WeatherDailyData getWeatherInfo() {

        return getWeatherClient.getWeather(
                getApiKeyFromFile(),
                "meteostat.p.rapidapi.com",
                "43.6667",
                "-79.4",
                "2020-01-01",
                "2020-01-31"
                );
    }

    private String getApiKeyFromFile() {
        List<String> lines =new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("apikey.txt").toURI()), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return lines.get(0);

    }


}
