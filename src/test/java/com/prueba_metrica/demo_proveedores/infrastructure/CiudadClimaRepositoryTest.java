package com.prueba_metrica.demo_proveedores.infrastructure;

import com.prueba_metrica.demo_proveedores.infrastructure.entity.CiudadClima;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CiudadClimaRepositoryTest {

    @Autowired
    private CiudadClimaRepository ciudadClimaRepository;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        ciudadClimaRepository.deleteAll();
    }

    @Test
    void findByNombreCiudadOrderByFechaConsultaDesc_returnsRecordsOrderedByQueryTimeDesc() {
        Instant older = Instant.parse("2024-01-01T12:00:00Z");
        Instant newer = Instant.parse("2024-01-02T12:00:00Z");
        CiudadClima malaga1 = new CiudadClima("Málaga", 36.72, -4.42, 18.0, 65, 12.0, older, "Europe/Madrid");
        CiudadClima malaga2 = new CiudadClima("Málaga", 36.72, -4.42, 19.0, 66, 11.0, newer, "Europe/Madrid");
        ciudadClimaRepository.save(malaga1);
        ciudadClimaRepository.save(malaga2);

        List<CiudadClima> result = ciudadClimaRepository.findByNombreCiudadOrderByFechaConsultaDesc("Málaga");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFechaConsulta()).isEqualTo(newer);
        assertThat(result.get(0).getTemperatura()).isEqualTo(19.0);
        assertThat(result.get(1).getFechaConsulta()).isEqualTo(older);
        assertThat(result.get(1).getTemperatura()).isEqualTo(18.0);
    }

    @Test
    void findByNombreCiudadOrderByFechaConsultaDesc_returnsEmptyForUnknownCity() {
        List<CiudadClima> result = ciudadClimaRepository.findByNombreCiudadOrderByFechaConsultaDesc("UnknownCity");

        assertThat(result).isEmpty();
    }

    @Test
    void save_persistsCiudadClima() {
        CiudadClima ciudad = new CiudadClima(
                "Milano", 45.46, 9.19, 8.2, 72, 5.5,
                Instant.now(), "Europe/Rome"
        );

        CiudadClima saved = ciudadClimaRepository.save(ciudad);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNombreCiudad()).isEqualTo("Milano");
        assertThat(saved.getTemperatura()).isEqualTo(8.2);
        assertThat(ciudadClimaRepository.findAll()).hasSize(1);
    }
}
