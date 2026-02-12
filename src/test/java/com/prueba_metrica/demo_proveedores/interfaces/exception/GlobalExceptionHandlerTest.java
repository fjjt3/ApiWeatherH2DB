package com.prueba_metrica.demo_proveedores.interfaces.exception;

import com.prueba_metrica.demo_proveedores.interfaces.exception.CityNotFoundException;
import com.prueba_metrica.demo_proveedores.interfaces.exception.ErrorResponse;
import com.prueba_metrica.demo_proveedores.interfaces.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final WebRequest request = mock(WebRequest.class);

    @Test
    void handleCityNotFoundException_returnsNotFound() {
        CityNotFoundException ex = new CityNotFoundException("City not found");
        when(request.getDescription(false)).thenReturn("uri=/test");

        ResponseEntity<ErrorResponse> response = handler.handleCityNotFoundException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(404);
        assertThat(response.getBody().error()).isEqualTo("Not Found");
        assertThat(response.getBody().message()).isEqualTo("City not found");
        assertThat(response.getBody().path()).isEqualTo("uri=/test");
    }

    @Test
    void handleIllegalArgumentException_returnsBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        when(request.getDescription(false)).thenReturn("uri=/test");

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgumentException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().message()).isEqualTo("Invalid argument");
    }

    @Test
    void handleGlobalException_returnsInternalServerError() {
        Exception ex = new Exception("Unexpected error");
        when(request.getDescription(false)).thenReturn("uri=/test");

        ResponseEntity<ErrorResponse> response = handler.handleGlobalException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(500);
        assertThat(response.getBody().message()).isEqualTo("Unexpected error");
    }
}
