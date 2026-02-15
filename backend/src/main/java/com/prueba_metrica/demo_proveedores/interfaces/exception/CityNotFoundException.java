package com.prueba_metrica.demo_proveedores.interfaces.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
