package com.simonova.weatherapp.controllers.model;

import lombok.Data;

@Data
public class WeatherRequest {
   private String city;
   private String startDate;
   private String endDate;

}
