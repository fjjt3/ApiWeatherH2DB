package com.prueba_metrica.demo_proveedores.interfaces;

import com.prueba_metrica.demo_proveedores.domain.CiudadClimaService;
import com.prueba_metrica.demo_proveedores.infrastructure.entity.CiudadClima;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CiudadClimaController.class)
class CiudadClimaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CiudadClimaService ciudadClimaService;

    @Test
    void listarTodos_returnsAllWeatherRecords() throws Exception {
        CiudadClima malaga = createCiudadClima(1L, "Málaga", 18.5);
        when(ciudadClimaService.listarTodos()).thenReturn(List.of(malaga));

        mockMvc.perform(get("/clima"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreCiudad").value("Málaga"))
                .andExpect(jsonPath("$[0].temperatura").value(18.5));
    }

    @Test
    void listarPorCiudad_returnsWeatherHistoryForCity() throws Exception {
        CiudadClima malaga = createCiudadClima(1L, "Málaga", 18.5);
        when(ciudadClimaService.listarPorCiudad("Málaga")).thenReturn(List.of(malaga));

        mockMvc.perform(get("/clima/ciudad/{nombreCiudad}", "Málaga")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreCiudad").value("Málaga"));
    }

    @Test
    void listarCiudadesConfiguradas_returnsConfiguredCityNames() throws Exception {
        when(ciudadClimaService.listarNombresCiudadesConfiguradas())
                .thenReturn(List.of("Málaga", "Milano", "Cortina d'Ampezzo"));

        mockMvc.perform(get("/clima/ciudades")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]").value("Málaga"))
                .andExpect(jsonPath("$[1]").value("Milano"))
                .andExpect(jsonPath("$[2]").value("Cortina d'Ampezzo"));
    }

    @Test
    void actualizarClimaTodasLasCiudades_returnsCreatedRecords() throws Exception {
        CiudadClima malaga = createCiudadClima(1L, "Málaga", 18.5);
        CiudadClima milano = createCiudadClima(2L, "Milano", 8.0);
        when(ciudadClimaService.actualizarYGuardarClimaCiudades())
                .thenReturn(List.of(malaga, milano));

        mockMvc.perform(post("/clima/actualizar")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreCiudad").value("Málaga"))
                .andExpect(jsonPath("$[1].nombreCiudad").value("Milano"));
    }

    @Test
    void actualizarClimaCiudad_returnsSingleCityWeather() throws Exception {
        CiudadClima malaga = createCiudadClima(1L, "Málaga", 18.5);
        when(ciudadClimaService.actualizarYGuardarPorCiudad("Málaga")).thenReturn(malaga);

        mockMvc.perform(post("/clima/actualizar/{nombreCiudad}", "Málaga")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCiudad").value("Málaga"))
                .andExpect(jsonPath("$.temperatura").value(18.5));
    }

    private static CiudadClima createCiudadClima(Long id, String cityName, double temperature) {
        CiudadClima c = new CiudadClima(
                cityName, 36.72, -4.42,
                temperature, 65, 12.0,
                Instant.now(), "Europe/Madrid"
        );
        c.setId(id);
        return c;
    }
}
