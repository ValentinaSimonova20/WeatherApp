package com.simonova.weatherapp.requests.location.model;

import lombok.Data;

import java.util.List;

@Data
public class LocationsData {
    List<LocationInfo> data;
}
