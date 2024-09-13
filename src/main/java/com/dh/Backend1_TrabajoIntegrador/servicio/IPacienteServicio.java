package com.dh.Backend1_TrabajoIntegrador.servicio;

import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;

import java.util.List;

public interface IPacienteServicio {
    Paciente consultarPorId(Long id);
    List<Paciente> consultarTodos();
    Paciente guardar(Paciente paciente);
    Paciente modificar(Paciente paciente);
    void eliminar(Long id);
}
