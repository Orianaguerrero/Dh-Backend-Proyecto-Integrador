package com.dh.Backend1_TrabajoIntegrador.servicio.implementacion;

import com.dh.Backend1_TrabajoIntegrador.entidad.Domicilio;
import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;
import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import com.dh.Backend1_TrabajoIntegrador.entidad.Turno;
import com.dh.Backend1_TrabajoIntegrador.servicio.IOdontologoServicio;
import com.dh.Backend1_TrabajoIntegrador.servicio.IPacienteServicio;
import com.dh.Backend1_TrabajoIntegrador.servicio.ITurnoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServicioImplTest {
    private ITurnoServicio turnoServicio;
    private IOdontologoServicio odontologoServicio;
    private IPacienteServicio pacienteServicio;

    @Autowired
    public TurnoServicioImplTest(ITurnoServicio turnoServicio, IOdontologoServicio odontologoServicio, IPacienteServicio pacienteServicio) {
        this.turnoServicio = turnoServicio;
        this.odontologoServicio = odontologoServicio;
        this.pacienteServicio = pacienteServicio;
    }

    @Test
    void testGuardar() {
        // arrange
        Turno turno = prepararTurno("111", "11111111");

        // act
        Long id = turnoServicio.guardar(turno).getId();

        // assert
        assert(id != null);
    }

    /*@Test(expected = IllegalArgumentException.class)
    void testGuardarNegativo() {
        // arrange
        Turno turno1 = prepararTurno("11", "1111111");
        Turno turno2 = prepararTurno("11", "1111111");

        // act
        Long id1 = turnoServicio.guardar(turno1).getId();
        Long id2 = turnoServicio.guardar(turno2).getId();

        // assert
        assert(id2 == null);
    }*/

    @Test
    void testConsultarPorId() {
        // arrange
        Turno turno = prepararTurno("222", "222222222");
        Long id = turnoServicio.guardar(turno).getId();

        Turno turnoEsperado = new Turno(
                id,
                odontologoServicio.consultarPorId(turno.getOdontologo().getId()),
                pacienteServicio.consultarPorId(turno.getPaciente().getId()),
                LocalDate.of(2031, 1, 1),
                LocalTime.of(20,0,0)
        );

        // act
        Turno turnoConsultado = turnoServicio.consultarPorId(id);

        // assert
        assertEquals(turnoEsperado.toString(), turnoConsultado.toString());
    }

    @Test
    void testConsultarTodos() {
        // arrange
        Turno turno = prepararTurno("333", "333333333");
        turnoServicio.guardar(turno);

        // act
        List<Turno> listaTurnos = turnoServicio.consultarTodos();

        // assert
        assert(!listaTurnos.isEmpty());
    }

    @Test
    void testModificar() {
        // arrange
        Turno turno = prepararTurno("444", "44444444");
        Long id = turnoServicio.guardar(turno).getId();

        Turno turnoConModificaciones = new Turno(
                id,
                new Odontologo(1L, null, null, null, null),
                new Paciente(1L, null, null, null, null, null, null),
                LocalDate.of(2039, 9, 9),
                LocalTime.of(9, 0, 0)
        );

        // act
        Turno turnoOriginal = turnoServicio.consultarPorId(id);
        turnoServicio.modificar(turnoConModificaciones);
        Turno turnoModificado = turnoServicio.consultarPorId(id);

        // assert
        assertNotEquals(turnoOriginal.toString(), turnoModificado.toString());
    }

    @Test
    void testEliminarPorId() {
        // arrange
        Turno turno = prepararTurno("555", "55555555");
        Long id = turnoServicio.guardar(turno).getId();

        int cantidadTurnosEsperada = turnoServicio.consultarTodos().size() - 1;

        // act
        turnoServicio.eliminar(id);
        int cantidadTurnos = turnoServicio.consultarTodos().size();

        // assert
        assert(cantidadTurnosEsperada == cantidadTurnos && cantidadTurnosEsperada >= 0);
    }

    Turno prepararTurno(String odontologo_matricula, String paciente_dni) {
        Odontologo odontologo = new Odontologo(null, "Anghelo", "Flores", odontologo_matricula, null);
        Domicilio domicilio = new Domicilio(null, "calle", 23, "localidad", "provincia");
        Paciente paciente = new Paciente(null, "Oriana", "Guerrero", domicilio, paciente_dni, LocalDate.now(), null);

        Long idOdontologo = odontologoServicio.guardar(odontologo).getId();
        Long idPaciente = pacienteServicio.guardar(paciente).getId();

        return new Turno(
                null,
                new Odontologo(idOdontologo, null, null, null, null),
                new Paciente(idPaciente, null, null, null, null, null, null),
                LocalDate.of(2031, 1, 1),
                LocalTime.of(20, 0, 0)
        );
    }
}