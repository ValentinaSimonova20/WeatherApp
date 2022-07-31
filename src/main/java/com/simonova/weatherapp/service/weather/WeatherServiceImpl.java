package com.simonova.weatherapp.service.weather;

import com.simonova.weatherapp.controllers.model.WeatherRequest;
import com.simonova.weatherapp.requests.location.model.LocationInfo;
import com.simonova.weatherapp.requests.weather.model.WeatherDailyData;
import com.simonova.weatherapp.service.coordinates.CoordinatesService;
import com.simonova.weatherapp.service.temperature.TemperatureService;
import com.simonova.weatherapp.service.weather.model.TemperatureInfoBySeason;
import com.simonova.weatherapp.service.weather.model.WeatherSeasonData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
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
        List<TemperatureInfoBySeason> temperatureInfoBySeasons = getTempInfoConnectedToSeason(weatherRequest);

        Map<List<String>,  String> minTempBySeasonAndYear= getMinTempBySeasonAndYear(temperatureInfoBySeasons);
        Map<List<String>,  String> maxTempBySeasonAndYear= getMaxTempBySeasonAndYear(temperatureInfoBySeasons);
        Map<List<String>,  String> avgTempBySeasonAndYear = getAvgTempBySeasonAndYear(temperatureInfoBySeasons);

        // connect three result
        getTemperaturesBySeason(minTempBySeasonAndYear, new ArrayList<>(), TemperatureInfoBySeason::setMinTemp);

        return null;
    }


    private List<TemperatureInfoBySeason> getTemperaturesBySeason(
            Map<List<String>,  String> mapToAddIntoResultList,
            List<TemperatureInfoBySeason> resultList,
            BiConsumer<TemperatureInfoBySeason, String> consumer
    ) {
        List<TemperatureInfoBySeason> result = mapToAddIntoResultList.entrySet().stream().peek(
                listStringEntry -> {

                }
        );
        return resultList;

    }

    /**
     * @return min temp of min temps
     */
    private Map<List<String>, String>  getMinTempBySeasonAndYear(List<TemperatureInfoBySeason> weatherInfo) {
        return getValueFromSummarize(
                getDefaultSummarize(
                        weatherInfo,
                        temp -> Double.parseDouble(temp.getMinTemp())
                ), temp -> String.valueOf(temp.getValue().getMin())
        );
    }

    /**
     * @return max temp of max temps
     */
    private Map<List<String>, String> getMaxTempBySeasonAndYear(List<TemperatureInfoBySeason> weatherInfo) {
        return getValueFromSummarize(
                getDefaultSummarize(
                        weatherInfo,
                        temp -> Double.parseDouble(temp.getMaxTemp())
                ), temp -> String.valueOf(temp.getValue().getMax())
        );
    }

    /**
     * @return avg temp of avg temps
     */
    private Map<List<String>, String> getAvgTempBySeasonAndYear(List<TemperatureInfoBySeason> weatherInfo) {
        return getValueFromSummarize(
                getDefaultSummarize(
                        weatherInfo,
                        temp -> Double.parseDouble(temp.getAvgTemp())
                ), temp -> String.valueOf(temp.getValue().getAverage())
        );
    }

    /**
     * @return key and certain value from summarize statistics
     */
    private Map<List<String>, String> getValueFromSummarize(
            Map<List<String>, DoubleSummaryStatistics> summarize,
            Function<Map.Entry<List<String>, DoubleSummaryStatistics>, String> valueMapper
    ) {
        return summarize
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, valueMapper));
    }

    /**
     * @return key and its summarize statistics by certain parameter
     */
    private Map<List<String>, DoubleSummaryStatistics> getDefaultSummarize(
            List<TemperatureInfoBySeason> weatherInfo,
            ToDoubleFunction<TemperatureInfoBySeason> mapper
    ) {
        return weatherInfo.stream().collect(Collectors.groupingBy(
                temperatureInfoBySeason -> Arrays.asList(
                        temperatureInfoBySeason.getSeason(),
                        temperatureInfoBySeason.getYear()
                ), Collectors.summarizingDouble(mapper))
        );
    }

    // get dailyInfo temperature. There are duplicates in season and year in result
    private List<TemperatureInfoBySeason> getTempInfoConnectedToSeason(WeatherRequest weatherRequest) {
        // now method return data by month
        //TODO: return data by seasons (winter, summer, etc)
        return getWeatherDailyData(weatherRequest).getData().stream()
                .map(temperatureDailyInfo ->
                        new TemperatureInfoBySeason(
                                getSeasonOfDate(temperatureDailyInfo.getDate()),
                                getYearOfDate(temperatureDailyInfo.getDate()),
                                temperatureDailyInfo.getTmax(),
                                temperatureDailyInfo.getTmin(),
                                temperatureDailyInfo.getTavg()
                        )).collect(Collectors.toList());

    }

    private String getSeasonOfDate(String date) {
        Month month = LocalDate.parse(date).getMonth();

        if(month == Month.DECEMBER || month == Month.JANUARY || month == Month.FEBRUARY) {
            return "Winter";
        }
        if(month == Month.MARCH || month == Month.APRIL || month == Month.MAY) {
            return "Spring";
        }
        if(month == Month.JUNE || month == Month.JULY || month == Month.AUGUST) {
            return "Summer";
        }
        return  "Autumn";
    }

    private String getYearOfDate(String date) {
        return String.valueOf(LocalDate.parse(date).getYear());
    }


}
