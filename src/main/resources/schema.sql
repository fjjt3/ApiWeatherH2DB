-- Weather project: city weather data from Open-Meteo
CREATE TABLE IF NOT EXISTS city_weather (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    temperature DOUBLE NOT NULL,
    humidity INT NOT NULL,
    wind_speed DOUBLE NOT NULL,
    query_time TIMESTAMP NOT NULL,
    timezone VARCHAR(100)
);
