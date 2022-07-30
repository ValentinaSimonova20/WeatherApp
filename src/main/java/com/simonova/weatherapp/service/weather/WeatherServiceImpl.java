package com.simonova.weatherapp.service.weather;

import com.simonova.weatherapp.controllers.model.WeatherRequest;
import com.simonova.weatherapp.requests.location.model.LocationInfo;
import com.simonova.weatherapp.requests.weather.model.TemperatureDailyInfo;
import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;
import com.simonova.weatherapp.service.coordinates.CoordinatesService;
import com.simonova.weatherapp.service.temperature.TemperatureService;
import com.simonova.weatherapp.service.weather.model.TemperatureDailyInfoBySeason;
import com.simonova.weatherapp.service.weather.model.WeatherSeasonData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final CoordinatesService coordinatesService;
    private final TemperatureService temperatureService;

    @Override
    public WeatherDailyData getWeatherDailyData(WeatherRequest weatherRequest) {
        return getWeatherDailyData(
                getLocationInfoByAddress(weatherRequest.getCity()),
                weatherRequest.getStartDate(),
                weatherRequest.getEndDate()
        );
    }

    private WeatherDailyData getWeatherDailyData(LocationInfo locationInfo, String startDate, String endDate) {
        return temperatureService.getWeatherDailyData(
                locationInfo.getLatitude(),
                locationInfo.getLongitude(),
                startDate,
                endDate
        );
    }

    private LocationInfo getLocationInfoByAddress(String address) {
        return coordinatesService.getCoordinatesByAddress(address);
    }

    @Override
    public WeatherSeasonData getWeatherSeasonData(WeatherRequest weatherRequest) {
        List<TemperatureDailyInfoBySeason> temperatureDailyInfoBySeasons = getTempInfoConnectedToSeason(weatherRequest);

        Map<List<String>,  Optional<TemperatureDailyInfoBySeason>> minTempBySeasonAndYear= getMinTempBySeasonAndYear(temperatureDailyInfoBySeasons);
        Map<List<String>,  Optional<TemperatureDailyInfoBySeason>> maxTempBySeasonAndYear= getMaxTempBySeasonAndYear(temperatureDailyInfoBySeasons);
        Map<List<String>,  Double> avgTempBySeasonAndYear = getAvgTempBySeasonAndYear(temperatureDailyInfoBySeasons);

        // connect three result
        Map<List<String>, TemperatureDailyInfoBySeason> result = minTempBySeasonAndYear.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> new TemperatureDailyInfoBySeason(
                                entry.getKey().get(0),
                                entry.getKey().get(1),
                                null,
                                entry.getValue().get().getMinTemp(),
                                null),
                        (oldValue, newValue) -> {
                            oldValue.setMinTemp(newValue.getMinTemp());
                            return oldValue;
                        }
                        ));
        return null;
    }

    // key is List with season and date
    private Map<List<String>, Optional<TemperatureDailyInfoBySeason>> getMinTempBySeasonAndYear(List<TemperatureDailyInfoBySeason> weatherInfo) {
        return getMapByCollector(weatherInfo, Collectors.minBy(Comparator.comparing(temp -> Double.parseDouble(temp.getMinTemp()))));
    }

    private Map<List<String>, Optional<TemperatureDailyInfoBySeason>> getMaxTempBySeasonAndYear(List<TemperatureDailyInfoBySeason> weatherInfo) {
        return getMapByCollector(weatherInfo, Collectors.maxBy((Comparator.comparing(temp -> Double.parseDouble(temp.getMaxTemp())))));
    }

    private Map<List<String>, Double> getAvgTempBySeasonAndYear(List<TemperatureDailyInfoBySeason> weatherInfo) {
        return getMapByCollectorAveragingDouble(weatherInfo, Collectors.averagingDouble(temp -> Double.parseDouble(temp.getAvgTemp())));
    }

    private <T>Map<List<String>, Optional<TemperatureDailyInfoBySeason>> getMapByCollector(
            List<TemperatureDailyInfoBySeason> weatherInfo,
            Collector<TemperatureDailyInfoBySeason, ?, Optional<TemperatureDailyInfoBySeason>> downstream
    ) {
        return getDefaultMap(weatherInfo, downstream);
    }

    private Map<List<String>, Double> getMapByCollectorAveragingDouble(
            List<TemperatureDailyInfoBySeason> weatherInfo,
            Collector<TemperatureDailyInfoBySeason, ?, Double> downStream
    ) {
        return getDefaultMap(weatherInfo, downStream);
    }

    private <T> Map<List<String>, T> getDefaultMap(
            List<TemperatureDailyInfoBySeason> weatherInfo,
            Collector<TemperatureDailyInfoBySeason, ?, T> downStream
    ) {
        return weatherInfo.stream().collect(Collectors.groupingBy(temperatureDailyInfoBySeason ->
                Arrays.asList(temperatureDailyInfoBySeason.getSeason(),
                        temperatureDailyInfoBySeason.getYear()), downStream));
    }

    // get dailyInfo temperature. There are duplicates in season and year in result
    private List<TemperatureDailyInfoBySeason> getTempInfoConnectedToSeason(WeatherRequest weatherRequest) {
        // now method return data by month
        //TODO: return data by seasons (winter, summer, etc)
        return getWeatherDailyData(weatherRequest).getData().stream()
                .map(temperatureDailyInfo ->
                        new TemperatureDailyInfoBySeason(
                                getSeasonOfDate(temperatureDailyInfo.getDate()),
                                getYearOfDate(temperatureDailyInfo.getDate()),
                                temperatureDailyInfo.getTmax(),
                                temperatureDailyInfo.getTmin(),
                                temperatureDailyInfo.getTavg()
                        )).collect(Collectors.toList());

    }

    private String getSeasonOfDate(String date) {
        return LocalDate.parse(date).getMonth().toString();
    }

    private String getYearOfDate(String date) {
        return String.valueOf(LocalDate.parse(date).getYear());
    }


}
