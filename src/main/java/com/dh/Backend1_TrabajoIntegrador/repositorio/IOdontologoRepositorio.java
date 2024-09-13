package com.dh.Backend1_TrabajoIntegrador.repositorio;

import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepositorio extends JpaRepository<Odontologo, Long> {
}
