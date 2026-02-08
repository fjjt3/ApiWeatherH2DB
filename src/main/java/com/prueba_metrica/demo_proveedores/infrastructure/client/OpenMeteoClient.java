package com.prueba_metrica.demo_proveedores.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenMeteoClient {

    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,relative_humidity_2m,wind_speed_10m&timezone={tz}";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openmeteo.timezone.default:Europe/Madrid}")
    private String defaultTimezone;

    public OpenMeteoResponse getCurrentWeather(double latitude, double longitude) {
        return getCurrentWeather(latitude, longitude, defaultTimezone);
    }

    public OpenMeteoResponse getCurrentWeather(double latitude, double longitude, String timezone) {
        return restTemplate.getForObject(FORECAST_URL,
                OpenMeteoResponse.class,
                latitude,
                longitude,
                timezone != null ? timezone : defaultTimezone);
    }
}
