package com.dh.Backend1_TrabajoIntegrador.servicio.implementacion;

import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;
import com.dh.Backend1_TrabajoIntegrador.excepcion.BadRequestException;
import com.dh.Backend1_TrabajoIntegrador.excepcion.ResourceNotFoundException;
import com.dh.Backend1_TrabajoIntegrador.repositorio.IOdontologoRepositorio;
import com.dh.Backend1_TrabajoIntegrador.servicio.IOdontologoServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoServicioImpl implements IOdontologoServicio {
    private IOdontologoRepositorio iOdontologoRepositorio;
    private static final Logger LOGGER = Logger.getLogger(OdontologoServicioImpl.class);

    @Autowired
    public OdontologoServicioImpl(IOdontologoRepositorio iOdontologoRepositorio) {
        this.iOdontologoRepositorio = iOdontologoRepositorio;
    }

    @Override
    public Odontologo consultarPorId(Long id) throws ResourceNotFoundException {

        // si id es null: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        Optional<Odontologo> odontologoBuscado =  iOdontologoRepositorio.findById(id);
        if (odontologoBuscado.isEmpty()) {
            LOGGER.error("No existe odontólogo, ID: " + id);
            throw new ResourceNotFoundException("No existe odontólogo, ID: " + id);
        }

        LOGGER.info("Odontólogo consultado con éxito: " + odontologoBuscado.get().toString());
        return odontologoBuscado.get();
    }

    @Override
    public List<Odontologo> consultarTodos() {

        List<Odontologo> odontologos = iOdontologoRepositorio.findAll();
        if (odontologos.isEmpty()) {
            LOGGER.warn("No se encontraron odontólogos");
        } else {
            LOGGER.info("Consulta de odontólogos exitosa. Cantidad de registros: " + odontologos.size());
        }

        return odontologos;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws BadRequestException {

        LOGGER.info("- Verificando que odontólogo no tenga un ID asignado ...");
        if (odontologo.getId() != null) {
            LOGGER.error("No se pudo guardar odontólogo, contiene ID: " + odontologo.toString());
            throw new BadRequestException("No se pudo guardar odontólogo, contiene ID: " + odontologo.toString());
        }

        // si matricula ya existe: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        Odontologo odontologoGuardado = iOdontologoRepositorio.save(odontologo);
        LOGGER.info("Odontólogo guardado con éxito: " + odontologoGuardado.toString());
        return odontologoGuardado;
    }

    @Override
    public Odontologo modificar(Odontologo odontologo) throws ResourceNotFoundException {

        LOGGER.info("- Verificando que odontólogo existe ...");
        consultarPorId(odontologo.getId());

        // si matricula ya existe pero en otro registro: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        Odontologo odontologoModificado = iOdontologoRepositorio.save(odontologo);
        LOGGER.info("Odontólogo modificado con éxito: " + odontologoModificado.toString());
        return odontologoModificado;
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {

        LOGGER.info("- Verificando que odontólogo existe ...");
        consultarPorId(id);

        // Si odontólogo tiene turnos registrados, no se podrá eliminar: la excepción
        // lo capturamos en processDataIntegrityViolationException en GlobalException

        iOdontologoRepositorio.deleteById(id);
        LOGGER.info("Odontólogo eliminado con éxito, ID: " + id);
    }
}
