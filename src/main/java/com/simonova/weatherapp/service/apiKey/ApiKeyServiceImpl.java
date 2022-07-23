package com.simonova.weatherapp.service.apiKey;

import com.simonova.weatherapp.service.apiKey.ApiKeyService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {
    @Override
    public String getApiKeyFromFile(String fileName) {
        List<String> lines =new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return lines.get(0);
    }
}
