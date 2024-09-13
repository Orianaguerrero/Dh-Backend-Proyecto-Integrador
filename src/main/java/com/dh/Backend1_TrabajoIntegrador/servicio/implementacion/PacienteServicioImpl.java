package com.dh.Backend1_TrabajoIntegrador.servicio.implementacion;

import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import com.dh.Backend1_TrabajoIntegrador.excepcion.BadRequestException;
import com.dh.Backend1_TrabajoIntegrador.excepcion.ResourceNotFoundException;
import com.dh.Backend1_TrabajoIntegrador.repositorio.IPacienteRepositorio;
import com.dh.Backend1_TrabajoIntegrador.servicio.IPacienteServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicioImpl implements IPacienteServicio {
    private IPacienteRepositorio iPacienteRepositorio;
    private static final Logger LOGGER = Logger.getLogger(PacienteServicioImpl.class);

    @Autowired
    public PacienteServicioImpl(IPacienteRepositorio iPacienteRepositorio) {
        this.iPacienteRepositorio = iPacienteRepositorio;
    }

    @Override
    public Paciente consultarPorId(Long id) throws ResourceNotFoundException {

        // si id es null: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        Optional<Paciente> pacienteBuscado =  iPacienteRepositorio.findById(id);
        if (pacienteBuscado.isEmpty()) {
            LOGGER.error("No existe paciente, ID: " + id);
            throw new ResourceNotFoundException("No existe paciente, ID: " + id);
        }

        LOGGER.info("Paciente consultado con éxito: " + pacienteBuscado.get().toString());
        return pacienteBuscado.get();
    }

    @Override
    public List<Paciente> consultarTodos() {

        List<Paciente> pacientes = iPacienteRepositorio.findAll();
        if (pacientes.isEmpty()) {
            LOGGER.warn("No se encontraron pacientes");
        } else {
            LOGGER.info("Consulta de pacientes exitosa. Cantidad de registros: " + pacientes.size());
        }

        return pacientes;
    }

    @Override
    public Paciente guardar(Paciente paciente) throws BadRequestException {

        LOGGER.info("- Verificando que paciente no tenga un ID asignado ...");
        if (paciente.getId() != null) {
            LOGGER.error("No se pudo guardar paciente, contiene ID: " + paciente.toString());
            throw new BadRequestException("No se pudo guardar paciente, contiene ID: " + paciente.toString());
        }

        LOGGER.info("- Verificando que domicilio del paciente no tenga un ID asignado ...");
        if (paciente.getDomicilio().getId() != null) {
            LOGGER.error("No se pudo guardar paciente, su domicilio contiene ID: " + paciente.toString());
            throw new BadRequestException("No se pudo guardar paciente, su domicilio contiene ID: " + paciente.toString());
        }

        // si dni ya existe: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        LOGGER.info("- Verificando que fecha sea hoy ...");
        if (!paciente.getFechaAlta().isEqual(LocalDate.now())) {
            LOGGER.error("La fecha debe ser hoy");
            throw new BadRequestException("La fecha debe ser hoy");
        }

        Paciente pacienteGuardado = iPacienteRepositorio.save(paciente);
        LOGGER.info("Paciente guardado con éxito: " + pacienteGuardado.toString());
        return pacienteGuardado;
    }

    @Override
    public Paciente modificar(Paciente paciente) throws ResourceNotFoundException, BadRequestException {

        LOGGER.info("- Verificando que paciente existe ...");
        Paciente pacienteConsultado = consultarPorId(paciente.getId());

        // si dni ya existe pero en otro registro: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        LOGGER.info("- Verificando que fecha alta no sea modificada ...");
        if (!paciente.getFechaAlta().isEqual(pacienteConsultado.getFechaAlta())) {
            LOGGER.error("La fecha alta no es igual a la fecha alta original");
            throw new BadRequestException("La fecha alta no debe ser modificada");
        }

        LOGGER.info("- Verificando que ID domicilio sea igual al original ...");
        if (paciente.getDomicilio().getId() != pacienteConsultado.getDomicilio().getId()) {
            LOGGER.error("El ID del domicilio es diferente al original");
            throw new BadRequestException("El ID del domicilio debe ser igual al original");
        }

        Paciente pacienteModificado = iPacienteRepositorio.save(paciente);
        LOGGER.info("Paciente modificado con éxito: " + pacienteModificado.toString());
        return pacienteModificado;
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {

        LOGGER.info("- Verificando que paciente existe ...");
        consultarPorId(id);

        // Si paciente tiene turnos registrados, no se podrá eliminar: la excepción
        // lo capturamos en processDataIntegrityViolationException en GlobalException

        // al eliminar paciente, su domicilio tambien se
        // elimina, no hace falta implementarlo

        iPacienteRepositorio.deleteById(id);
        LOGGER.info("Paciente eliminado con éxito, ID: " + id);
    }
}
