package com.dh.Backend1_TrabajoIntegrador.controlador;

import com.dh.Backend1_TrabajoIntegrador.entidad.Turno;
import com.dh.Backend1_TrabajoIntegrador.servicio.ITurnoServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoControlador {
    private ITurnoServicio turnoServicio;
    private static final Logger LOGGER = Logger.getLogger(TurnoControlador.class);

    @Autowired
    public TurnoControlador(ITurnoServicio turnoServicio) {
        this.turnoServicio = turnoServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> consultarPorId(@PathVariable Long id) {
        LOGGER.info("--> CONSULTAR TURNO");

        return ResponseEntity
                .ok(turnoServicio.consultarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Turno>> consultarTodos() {
        LOGGER.info("--> CONSULTAR TODOS LOS TURNOS");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(turnoServicio.consultarTodos());
    }

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        LOGGER.info("--> GUARDAR TURNO");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(turnoServicio.guardar(turno));
    }

    @PutMapping
    public ResponseEntity<Turno> modificar(@RequestBody Turno turno) {
        LOGGER.info("--> MODIFICAR TURNO");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(turnoServicio.modificar(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        LOGGER.info("--> ELIMINAR TURNO");

        turnoServicio.eliminar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
