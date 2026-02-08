package com.prueba_metrica.demo_proveedores.infrastructure.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "city_weather")
public class CiudadClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String nombreCiudad;

    @Column(name = "latitude", nullable = false)
    private Double latitud;

    @Column(name = "longitude", nullable = false)
    private Double longitud;

    @Column(name = "temperature", nullable = false)
    private Double temperatura;

    @Column(name = "humidity", nullable = false)
    private Integer humedad;

    @Column(name = "wind_speed", nullable = false)
    private Double velocidadViento;

    @Column(name = "query_time", nullable = false)
    private Instant fechaConsulta;

    @Column(name = "timezone")
    private String timezoneMeteo;

    public CiudadClima() {
    }

    public CiudadClima(String nombreCiudad, Double latitud, Double longitud,
                       Double temperatura, Integer humedad, Double velocidadViento,
                       Instant fechaConsulta, String timezoneMeteo) {
        this.nombreCiudad = nombreCiudad;
        this.latitud = latitud;
        this.longitud = longitud;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.velocidadViento = velocidadViento;
        this.fechaConsulta = fechaConsulta;
        this.timezoneMeteo = timezoneMeteo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getHumedad() {
        return humedad;
    }

    public void setHumedad(Integer humedad) {
        this.humedad = humedad;
    }

    public Double getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(Double velocidadViento) {
        this.velocidadViento = velocidadViento;
    }

    public Instant getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Instant fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getTimezoneMeteo() {
        return timezoneMeteo;
    }

    public void setTimezoneMeteo(String timezoneMeteo) {
        this.timezoneMeteo = timezoneMeteo;
    }
}
