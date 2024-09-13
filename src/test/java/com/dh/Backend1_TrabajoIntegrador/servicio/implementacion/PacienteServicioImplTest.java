package com.dh.Backend1_TrabajoIntegrador.servicio.implementacion;

import com.dh.Backend1_TrabajoIntegrador.entidad.Domicilio;
import com.dh.Backend1_TrabajoIntegrador.entidad.Paciente;
import com.dh.Backend1_TrabajoIntegrador.servicio.IPacienteServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PacienteServicioImplTest {
    private IPacienteServicio pacienteServicio;

    @Autowired
    public PacienteServicioImplTest(IPacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }
    @Test
    void testGuardar() {
        // arrange
        Domicilio domicilio = new Domicilio(null,"La palmita",111,"Merida","El vigia");
        Paciente paciente = new Paciente(null,"Maria","Perez", domicilio,"1111", LocalDate.now(),null);

        // act
        Long id = pacienteServicio.guardar(paciente).getId();

        // assert
        assert(id != null);
    }
    @Test
    void testConsultarPorId() {
        // arrange
        Domicilio domicilio = new Domicilio(null,"La palmita",222,"Merida","El vigia");
        Paciente paciente = new Paciente(null,"Elvis","Araque", domicilio,"2222", LocalDate.now(),null);

        Long id = pacienteServicio.guardar(paciente).getId();

        Paciente pacienteEsperado = new Paciente(
                id,
                "Elvis",
                "Araque",
                domicilio,
                "2222",
                LocalDate.now(),
                null
        );

        // act
        Paciente pacienteConsultado = pacienteServicio.consultarPorId(id);

        // assert
        assertEquals(pacienteEsperado.toString(), pacienteConsultado.toString());
    }
    @Test
    void testConsultarTodos() {
        // arrange
        Domicilio domicilio = new Domicilio(null,"La palmita",333,"Merida","El vigia");
        Paciente paciente = new Paciente(null,"Fernando","Araque", domicilio,"3333", LocalDate.now(),null);
        pacienteServicio.guardar(paciente);

        // act
        List<Paciente> listaPacientes = pacienteServicio.consultarTodos();

        // assert
        assert(!listaPacientes.isEmpty());
    }
    @Test
    void testModificar() {
        // arrange
        Domicilio domicilio = new Domicilio(null,"La palmita",444,"Merida","El vigia");
        Paciente paciente = new Paciente(null,"Ana","Gomez", domicilio,"4444", LocalDate.now(),null);
        Long id = pacienteServicio.guardar(paciente).getId();

        Paciente pacienteConModificaciones = new Paciente(
                id,
                "Ana modificada",
                "Gomez",
                new Domicilio(
                        paciente.getDomicilio().getId(),
                        "La palmita",
                        444,
                        "Merida",
                        "El vigia"
                ),
                "8888",
                LocalDate.now(),
                null
        );

        // act
        Paciente pacienteOriginal = pacienteServicio.consultarPorId(id);
        pacienteServicio.modificar(pacienteConModificaciones);
        Paciente pacienteModificado = pacienteServicio.consultarPorId(id);

        // assert
        assertNotEquals(pacienteOriginal.toString(), pacienteModificado.toString());
    }

    @Test
    void testEliminarPorId() {
        // arrange
        Domicilio domicilio = new Domicilio(null,"La palmita",555,"Merida","El vigia");
        Paciente paciente = new Paciente(null,"Ana","Gomez", domicilio,"5555", LocalDate.now(),null);
        Long id = pacienteServicio.guardar(paciente).getId();

        int cantidadPacientesEsperada = pacienteServicio.consultarTodos().size() - 1;

        // act
        pacienteServicio.eliminar(id);
        int cantidadPacientes = pacienteServicio.consultarTodos().size();

        // assert
        assert(cantidadPacientesEsperada == cantidadPacientes && cantidadPacientesEsperada >= 0);
    }
}