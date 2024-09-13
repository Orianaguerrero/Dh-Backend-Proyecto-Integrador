package com.dh.Backend1_TrabajoIntegrador.servicio;

import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;

import java.util.List;

public interface IOdontologoServicio {
    Odontologo consultarPorId(Long id);
    List<Odontologo> consultarTodos();
    Odontologo guardar(Odontologo odontologo);
    Odontologo modificar(Odontologo odontologo);
    void eliminar(Long id);
}
