package com.simonova.weatherapp.service.coordinates;

import com.simonova.weatherapp.requests.location.GetLocationClient;
import com.simonova.weatherapp.requests.location.model.LocationInfo;
import com.simonova.weatherapp.service.apiKey.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoordinatesServiceImpl implements CoordinatesService {
    private final ApiKeyService apiKeyService;
    private final GetLocationClient getLocationClient;

    @Override
    public LocationInfo getCoordinatesByAddress(String address) {
        return getLocationClient.getLocationData(
                address,
                apiKeyService.getApiKeyFromFile("locationApikey.txt")
        ).getData().get(0);
    }
}
