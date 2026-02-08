package com.prueba_metrica.demo_proveedores.domain;

/**
 * Configuration for a city to query Open-Meteo.
 */
public class CiudadConfig {
    private final String nombre;
    private final double latitud;
    private final double longitud;
    private final String timezone;

    public CiudadConfig(String nombre, double latitud, double longitud, String timezone) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.timezone = timezone;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getTimezone() {
        return timezone;
    }
}
