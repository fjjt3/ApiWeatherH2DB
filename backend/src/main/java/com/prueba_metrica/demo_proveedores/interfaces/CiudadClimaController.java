package com.prueba_metrica.demo_proveedores.interfaces;

import com.prueba_metrica.demo_proveedores.domain.CiudadClimaService;
import com.prueba_metrica.demo_proveedores.infrastructure.entity.CiudadClima;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clima")
public class CiudadClimaController {

    private final CiudadClimaService ciudadClimaService;

    public CiudadClimaController(CiudadClimaService ciudadClimaService) {
        this.ciudadClimaService = ciudadClimaService;
    }

    /**
     * Calls Open-Meteo for Málaga, Milano and Cortina, saves to H2 and returns the created records.
     */
    @PostMapping("/actualizar")
    public ResponseEntity<List<CiudadClima>> actualizarClimaTodasLasCiudades() {
        return ResponseEntity.ok(ciudadClimaService.actualizarYGuardarClimaCiudades());
    }

    /**
     * Updates and saves the weather for a city by name (Málaga, Milano, Cortina d'Ampezzo).
     */
    @PostMapping("/actualizar/{nombreCiudad}")
    public ResponseEntity<CiudadClima> actualizarClimaCiudad(@PathVariable String nombreCiudad) {
        return ResponseEntity.ok(ciudadClimaService.actualizarYGuardarPorCiudad(nombreCiudad));
    }

    /**
     * Lists all weather records stored in H2.
     */
    @GetMapping
    public List<CiudadClima> listarTodos() {
        return ciudadClimaService.listarTodos();
    }

    /**
     * Lists the weather query history for a city.
     */
    @GetMapping("/ciudad/{nombreCiudad}")
    public List<CiudadClima> listarPorCiudad(@PathVariable String nombreCiudad) {
        return ciudadClimaService.listarPorCiudad(nombreCiudad);
    }

    /**
     * Lists the configured city names (Málaga, Milano, Cortina d'Ampezzo).
     */
    @GetMapping("/ciudades")
    public List<String> listarCiudadesConfiguradas() {
        return ciudadClimaService.listarNombresCiudadesConfiguradas();
    }
}
