package com.dh.Backend1_TrabajoIntegrador.servicio.implementacion;

import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;
import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import com.dh.Backend1_TrabajoIntegrador.entidad.Turno;
import com.dh.Backend1_TrabajoIntegrador.excepcion.BadRequestException;
import com.dh.Backend1_TrabajoIntegrador.excepcion.ResourceNotFoundException;
import com.dh.Backend1_TrabajoIntegrador.repositorio.ITurnoRepositorio;
import com.dh.Backend1_TrabajoIntegrador.servicio.IOdontologoServicio;
import com.dh.Backend1_TrabajoIntegrador.servicio.IPacienteServicio;
import com.dh.Backend1_TrabajoIntegrador.servicio.ITurnoServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoServicioImpl implements ITurnoServicio {
    private ITurnoRepositorio iTurnoRepositorio;
    private IPacienteServicio iPacienteServicio;
    private IOdontologoServicio iOdontologoServicio;
    private static final Logger LOGGER = Logger.getLogger(TurnoServicioImpl.class);

    @Autowired
    public TurnoServicioImpl(ITurnoRepositorio iTurnoRepositorio, IPacienteServicio iPacienteServicio, IOdontologoServicio iOdontologoServicio) {
        this.iTurnoRepositorio = iTurnoRepositorio;
        this.iPacienteServicio = iPacienteServicio;
        this.iOdontologoServicio = iOdontologoServicio;
    }

    @Override
    public Turno consultarPorId(Long id) throws ResourceNotFoundException {

        // si id es null: la excepción lo capturamos en
        // processDataIntegrityViolationException en GlobalException

        Optional<Turno> turnoBuscado =  iTurnoRepositorio.findById(id);
        if (turnoBuscado.isEmpty()) {
            LOGGER.error("No existe turno, ID: " + id);
            throw new ResourceNotFoundException("No existe turno, ID: " + id);
        }

        LOGGER.info("Turno consultado con éxito: " + turnoBuscado.get().toString());
        return turnoBuscado.get();
    }

    @Override
    public List<Turno> consultarTodos() {

        List<Turno> turnos = iTurnoRepositorio.findAll();
        if (turnos.isEmpty()) {
            LOGGER.warn("No se encontraron turnos");
        } else {
            LOGGER.info("Consulta de turnos exitosa. Cantidad de registros: " + turnos.size());
        }

        return turnos;
    }

    @Override
    public Turno guardar(Turno turno) throws ResourceNotFoundException, BadRequestException {

        LOGGER.info("- Verificando que turno no tenga un ID asignado ...");
        if (turno.getId() != null) {
            LOGGER.error("No se pudo guardar turno, contiene ID: " + turno.toString());
            throw new BadRequestException("No se pudo guardar turno, contiene ID: " + turno.toString());
        }

        LOGGER.info("- Verificando que odontólogo existe ...");
        Odontologo odontologo = iOdontologoServicio.consultarPorId(turno.getOdontologo().getId());

        LOGGER.info("- Verificando que paciente existe ...");
        Paciente paciente = iPacienteServicio.consultarPorId(turno.getPaciente().getId());

        LOGGER.info("- Verificando que fecha sea a partir de hoy ...");
        if (turno.getFecha().isBefore(LocalDate.now())) {
            LOGGER.error("La fecha debe ser a partir de hoy");
            throw new BadRequestException("La fecha debe ser a partir de hoy");
        }

        LOGGER.info("- Verificando que la hora sea futura ...");
        if (turno.getFecha().isEqual(LocalDate.now()) && !turno.getHora().isAfter(LocalTime.now())) {
            LOGGER.error("La hora debe ser futura");
            throw new BadRequestException("La hora debe ser futura");
        }

        LOGGER.info("- Verificando si el odontólogo en la fecha y hora está disponible ...");
        if (!verificarFechaHoraDispnible(odontologo.getId(), turno.getFecha(), turno.getHora())) {
            LOGGER.error("El odontólogo no está disponible en esa fecha y hora: " + turno.toString());
            throw new BadRequestException("El odontólogo no está disponible en esa fecha y hora: " + turno.toString());
        }

        Turno turnoGuardado = iTurnoRepositorio.save(turno);
        turnoGuardado.setOdontologo(odontologo);
        turnoGuardado.setPaciente(paciente);
        LOGGER.info("Turno guardado con éxito: " + turnoGuardado.toString());
        return turnoGuardado;
    }

    private Boolean verificarFechaHoraDispnible(Long idOdontologo, LocalDate fecha, LocalTime hora) {
        Boolean respuesta = true;
        List<Turno> listaTurnos = iTurnoRepositorio.findAll();
        for (Turno turno : listaTurnos) {
            if (turno.getOdontologo().getId() == idOdontologo && turno.getFecha().equals(fecha) && turno.getHora().equals(hora)) {
                respuesta = false;
            }
        }
        return respuesta;
    }

    @Override
    public Turno modificar(Turno turno) throws ResourceNotFoundException, BadRequestException {

        LOGGER.info("- Verificando que turno existe ...");
        consultarPorId(turno.getId());

        LOGGER.info("- Verificando que odontólogo existe ...");
        iOdontologoServicio.consultarPorId(turno.getOdontologo().getId());

        LOGGER.info("- Verificando que paciente existe ...");
        iPacienteServicio.consultarPorId(turno.getPaciente().getId());

        LOGGER.info("- Verificando que fecha sea a partir de hoy ...");
        if (turno.getFecha().isBefore(LocalDate.now())) {
            LOGGER.error("La fecha debe ser a partir de hoy");
            throw new BadRequestException("La fecha debe ser a partir de hoy");
        }

        LOGGER.info("- Verificando que la hora sea futura ...");
        if (turno.getFecha().isEqual(LocalDate.now()) && !turno.getHora().isAfter(LocalTime.now())) {
            LOGGER.error("La hora debe ser futura");
            throw new BadRequestException("La hora debe ser futura");
        }

        Turno turnoModificado = iTurnoRepositorio.save(turno);
        LOGGER.info("Turno modificado con éxito: " + turnoModificado.toString());
        return turnoModificado;
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {

        LOGGER.info("- Verificando que turno existe ...");
        consultarPorId(id);

        iTurnoRepositorio.deleteById(id);
        LOGGER.info("Turno eliminado con éxito, ID: " + id);
    }
}
