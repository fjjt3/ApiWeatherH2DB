package com.prueba_metrica.demo_proveedores.domain;

import com.prueba_metrica.demo_proveedores.infrastructure.CiudadClimaRepository;
import com.prueba_metrica.demo_proveedores.infrastructure.client.OpenMeteoClient;
import com.prueba_metrica.demo_proveedores.infrastructure.client.OpenMeteoResponse;
import com.prueba_metrica.demo_proveedores.infrastructure.entity.CiudadClima;
import com.prueba_metrica.demo_proveedores.interfaces.exception.CityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CiudadClimaServiceTest {

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private CiudadClimaRepository ciudadClimaRepository;

    @InjectMocks
    private CiudadClimaService ciudadClimaService;

    @Test
    void listarTodos_returnsAllFromRepository() {
        CiudadClima malaga = new CiudadClima("Málaga", 36.72, -4.42, 18.5, 65, 12.0, null, "Europe/Madrid");
        when(ciudadClimaRepository.findAll()).thenReturn(List.of(malaga));

        List<CiudadClima> result = ciudadClimaService.listarTodos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombreCiudad()).isEqualTo("Málaga");
        verify(ciudadClimaRepository).findAll();
    }

    @Test
    void listarPorCiudad_normalizesCortinaToCortinaDAmpezzo() {
        when(ciudadClimaRepository.findByNombreCiudadOrderByFechaConsultaDesc("Cortina d'Ampezzo"))
                .thenReturn(List.of());

        ciudadClimaService.listarPorCiudad("Cortina");

        verify(ciudadClimaRepository).findByNombreCiudadOrderByFechaConsultaDesc("Cortina d'Ampezzo");
    }

    @Test
    void listarNombresCiudadesConfiguradas_returnsMalagaMilanoCortina() {
        List<String> result = ciudadClimaService.listarNombresCiudadesConfiguradas();

        assertThat(result).containsExactly("Málaga", "Milano", "Cortina d'Ampezzo", "Los Villares en Jaén");
    }

    @Test
    void actualizarYGuardarPorCiudad_unknownCity_throwsCityNotFoundException() {
        assertThatThrownBy(() -> ciudadClimaService.actualizarYGuardarPorCiudad("UnknownCity"))
                .isInstanceOf(CityNotFoundException.class)
                .hasMessageContaining("City not configured");
    }

    @Test
    void actualizarYGuardarPorCiudad_cortinaAlias_acceptsAndFetches() {
        OpenMeteoResponse response = createOpenMeteoResponse(46.54, 12.14, -2.0, 80, 8.0, "Europe/Rome");
        CiudadClima saved = new CiudadClima("Cortina d'Ampezzo", 46.54, 12.14, -2.0, 80, 8.0, null, "Europe/Rome");
        when(openMeteoClient.getCurrentWeather(46.54, 12.14, "Europe/Rome")).thenReturn(response);
        when(ciudadClimaRepository.save(any(CiudadClima.class))).thenReturn(saved);

        CiudadClima result = ciudadClimaService.actualizarYGuardarPorCiudad("Cortina");

        assertThat(result.getNombreCiudad()).isEqualTo("Cortina d'Ampezzo");
        verify(openMeteoClient).getCurrentWeather(46.54, 12.14, "Europe/Rome");
        verify(ciudadClimaRepository).save(any(CiudadClima.class));
    }

    @Test
    void actualizarYGuardarClimaCiudades_callsClientAndSavesForEachCity() {
        OpenMeteoResponse response = createOpenMeteoResponse(36.72, -4.42, 18.5, 65, 12.0, "Europe/Madrid");
        when(openMeteoClient.getCurrentWeather(anyDouble(), anyDouble(), any())).thenReturn(response);
        when(ciudadClimaRepository.save(any(CiudadClima.class))).thenAnswer(inv -> inv.getArgument(0));

        List<CiudadClima> result = ciudadClimaService.actualizarYGuardarClimaCiudades();

        assertThat(result).hasSize(4);
        verify(ciudadClimaRepository, times(4)).save(any(CiudadClima.class));
    }

    private static OpenMeteoResponse createOpenMeteoResponse(double lat, double lon, double temp, int humidity,
            double wind, String tz) {
        OpenMeteoResponse response = new OpenMeteoResponse();
        response.setLatitude(lat);
        response.setLongitude(lon);
        response.setTimezone(tz);
        OpenMeteoResponse.CurrentWeather current = new OpenMeteoResponse.CurrentWeather();
        current.setTemperature2m(temp);
        current.setRelativeHumidity2m(humidity);
        current.setWindSpeed10m(wind);
        response.setCurrent(current);
        return response;
    }
}
