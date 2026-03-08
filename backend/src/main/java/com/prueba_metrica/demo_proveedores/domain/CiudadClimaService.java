package com.prueba_metrica.demo_proveedores.domain;

import com.prueba_metrica.demo_proveedores.infrastructure.CiudadClimaRepository;
import com.prueba_metrica.demo_proveedores.infrastructure.client.OpenMeteoClient;
import com.prueba_metrica.demo_proveedores.infrastructure.client.OpenMeteoResponse;
import com.prueba_metrica.demo_proveedores.infrastructure.entity.CiudadClima;
import com.prueba_metrica.demo_proveedores.interfaces.exception.CityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CiudadClimaService {

    private final OpenMeteoClient openMeteoClient;
    private final CiudadClimaRepository ciudadClimaRepository;
    private final List<CiudadConfig> ciudadesConfiguradas;

    public CiudadClimaService(OpenMeteoClient openMeteoClient,
            CiudadClimaRepository ciudadClimaRepository) {
        this.openMeteoClient = openMeteoClient;
        this.ciudadClimaRepository = ciudadClimaRepository;
        this.ciudadesConfiguradas = List.of(
                new CiudadConfig("Málaga", 36.72, -4.42, "Europe/Madrid"),
                new CiudadConfig("Milano", 45.46, 9.19, "Europe/Rome"),
                new CiudadConfig("Cortina d'Ampezzo", 46.54, 12.14, "Europe/Rome"),
                new CiudadConfig("Los Villares en Jaén", 37.5856, -3.8172, "Europe/Madrid")
        );
    }

    /**
     * Fetches current weather from Open-Meteo for all configured cities and saves
     * to H2.
     */
    public List<CiudadClima> actualizarYGuardarClimaCiudades() {
        return ciudadesConfiguradas.stream()
                .map(this::fetchAndSave)
                .toList();
    }

    /**
     * Fetches and saves the weather for a city by name (must be in the configured
     * list).
     * Accepts "Cortina" as an alias for "Cortina d'Ampezzo".
     */
    public CiudadClima actualizarYGuardarPorCiudad(String nombreCiudad) {
        String nombre = normalizarNombreCiudad(nombreCiudad);
        return ciudadesConfiguradas.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .map(this::fetchAndSave)
                .orElseThrow(() -> new CityNotFoundException(
                        "City not configured: " + nombreCiudad));
    }

    private static String normalizarNombreCiudad(String nombre) {
        if ("Cortina".equalsIgnoreCase(nombre)) {
            return "Cortina d'Ampezzo";
        }
        return nombre;
    }

    private CiudadClima fetchAndSave(CiudadConfig config) {
        OpenMeteoResponse response = openMeteoClient.getCurrentWeather(
                config.getLatitud(),
                config.getLongitud(),
                config.getTimezone());
        if (response == null || response.getCurrent() == null) {
            throw new IllegalStateException("No Open-Meteo data for " + config.getNombre());
        }
        OpenMeteoResponse.CurrentWeather current = response.getCurrent();
        CiudadClima entity = new CiudadClima(
                config.getNombre(),
                response.getLatitude(),
                response.getLongitude(),
                current.getTemperature2m(),
                current.getRelativeHumidity2m(),
                current.getWindSpeed10m(),
                Instant.now(),
                response.getTimezone());
        return ciudadClimaRepository.save(entity);
    }

    public List<CiudadClima> listarTodos() {
        return ciudadClimaRepository.findAll();
    }

    public List<CiudadClima> listarPorCiudad(String nombreCiudad) {
        String nombre = normalizarNombreCiudad(nombreCiudad);
        return ciudadClimaRepository.findByNombreCiudadOrderByFechaConsultaDesc(nombre);
    }

    public List<String> listarNombresCiudadesConfiguradas() {
        return ciudadesConfiguradas.stream().map(CiudadConfig::getNombre).toList();
    }
}
