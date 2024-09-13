package com.dh.Backend1_TrabajoIntegrador.repositorio;

import com.dh.Backend1_TrabajoIntegrador.entidad.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepositorio extends JpaRepository<Turno, Long> {
}
