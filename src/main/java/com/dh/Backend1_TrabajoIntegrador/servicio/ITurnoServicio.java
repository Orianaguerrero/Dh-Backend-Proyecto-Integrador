package com.dh.Backend1_TrabajoIntegrador.servicio;

import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import com.dh.Backend1_TrabajoIntegrador.entidad.Turno;

import java.util.List;

public interface ITurnoServicio {
    Turno consultarPorId(Long id);
    List<Turno> consultarTodos();
    Turno guardar(Turno turno);
    Turno modificar(Turno turno);
    void eliminar(Long id);
}
