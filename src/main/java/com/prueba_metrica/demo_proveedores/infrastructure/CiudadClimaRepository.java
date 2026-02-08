package com.prueba_metrica.demo_proveedores.infrastructure;

import com.prueba_metrica.demo_proveedores.infrastructure.entity.CiudadClima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadClimaRepository extends JpaRepository<CiudadClima, Long> {

    List<CiudadClima> findByNombreCiudadOrderByFechaConsultaDesc(String nombreCiudad);
}
