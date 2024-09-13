package com.dh.Backend1_TrabajoIntegrador.controlador;

import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;
import com.dh.Backend1_TrabajoIntegrador.servicio.IOdontologoServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoControlador {
    private IOdontologoServicio odontologoServicio;
    private static final Logger LOGGER = Logger.getLogger(OdontologoControlador.class);

    @Autowired
    public OdontologoControlador(IOdontologoServicio odontologoServicio) {
        this.odontologoServicio = odontologoServicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> consultarPorId(@PathVariable Long id) {
        LOGGER.info("--> CONSULTAR ODONTÓLOGO");

        return ResponseEntity
                .ok(odontologoServicio.consultarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> consultarTodos() {
        LOGGER.info("--> CONSULTAR TODOS LOS ODONTÓLOGOS");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(odontologoServicio.consultarTodos());
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        LOGGER.info("--> GUARDAR ODONTÓLOGO");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(odontologoServicio.guardar(odontologo));
    }

    @PutMapping
    public ResponseEntity<Odontologo> modificar(@RequestBody Odontologo odontologo) {
        LOGGER.info("--> MODIFICAR ODONTÓLOGO");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(odontologoServicio.modificar(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        LOGGER.info("--> ELIMINAR ODONTÓLOGO");

        odontologoServicio.eliminar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
