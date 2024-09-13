package com.dh.Backend1_TrabajoIntegrador.repositorio;

import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepositorio extends JpaRepository<Paciente, Long> {
}
