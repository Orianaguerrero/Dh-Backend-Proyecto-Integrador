package com.dh.Backend1_TrabajoIntegrador.controlador;

import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import com.dh.Backend1_TrabajoIntegrador.servicio.IPacienteServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteControlador {
    private IPacienteServicio pacienteServicio;
    private static final Logger LOGGER = Logger.getLogger(PacienteControlador.class);

    @Autowired
    public PacienteControlador(IPacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> consultarPorId(@PathVariable Long id) {
        LOGGER.info("--> CONSULTAR PACIENTE");

        return ResponseEntity
                .ok(pacienteServicio.consultarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> consultarTodos() {
        LOGGER.info("--> CONSULTAR TODOS LOS PACIENTES");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pacienteServicio.consultarTodos());
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        LOGGER.info("--> GUARDAR PACIENTE");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pacienteServicio.guardar(paciente));
    }

    @PutMapping
    public ResponseEntity<Paciente> modificar(@RequestBody Paciente paciente) {
        LOGGER.info("--> MODIFICAR PACIENTE");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pacienteServicio.modificar(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        LOGGER.info("--> ELIMINAR PACIENTE");

        pacienteServicio.eliminar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
