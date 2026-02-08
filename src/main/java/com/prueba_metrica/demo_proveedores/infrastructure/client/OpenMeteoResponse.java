package com.prueba_metrica.demo_proveedores.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMeteoResponse {

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("current")
    private CurrentWeather current;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentWeather {
        @JsonProperty("time")
        private String time;

        @JsonProperty("temperature_2m")
        private Double temperature2m;

        @JsonProperty("relative_humidity_2m")
        private Integer relativeHumidity2m;

        @JsonProperty("wind_speed_10m")
        private Double windSpeed10m;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Double getTemperature2m() {
            return temperature2m;
        }

        public void setTemperature2m(Double temperature2m) {
            this.temperature2m = temperature2m;
        }

        public Integer getRelativeHumidity2m() {
            return relativeHumidity2m;
        }

        public void setRelativeHumidity2m(Integer relativeHumidity2m) {
            this.relativeHumidity2m = relativeHumidity2m;
        }

        public Double getWindSpeed10m() {
            return windSpeed10m;
        }

        public void setWindSpeed10m(Double windSpeed10m) {
            this.windSpeed10m = windSpeed10m;
        }
    }
}
