package com.simonova.weatherapp.requests.location;

import com.simonova.weatherapp.requests.location.model.LocationsData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "location", url = "${location.api.baseUrl}")
public interface GetLocationClient {

    String QUERY = "query";
    String ACCESS_KEY = "access_key";
    String GET_COORDINATES_URL = "/v1/forward";

    @RequestMapping(
            method = RequestMethod.GET,
            value = GET_COORDINATES_URL,
            params = {QUERY, ACCESS_KEY}
    )
    LocationsData getLocationData(@RequestParam(QUERY) String query,@RequestParam(ACCESS_KEY) String accessKey);
}
