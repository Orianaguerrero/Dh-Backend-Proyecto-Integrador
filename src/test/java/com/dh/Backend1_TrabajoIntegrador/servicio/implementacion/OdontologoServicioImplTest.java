package com.dh.Backend1_TrabajoIntegrador.servicio.implementacion;

import com.dh.Backend1_TrabajoIntegrador.entidad.Odontologo;
import com.dh.Backend1_TrabajoIntegrador.servicio.IOdontologoServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServicioImplTest {
    private IOdontologoServicio odontologoServicio;

    @Autowired
    public OdontologoServicioImplTest(IOdontologoServicio odontologoServicio) {
        this.odontologoServicio = odontologoServicio;
    }
    @Test
    void testGuardar() {
        // arrange
        Odontologo odontologo = new Odontologo(null,"Oriana","Guerrero","111",null);

        // act
        Long id = odontologoServicio.guardar(odontologo).getId();

        // assert
        assert(id != null);
    }
    @Test
    void testConsultarPorId() {
        // arrange
        Odontologo odontologo = new Odontologo(null,"Kitty","Santiago","222",null);
        Long id = odontologoServicio.guardar(odontologo).getId();

        Odontologo odontologoEsperado = new Odontologo(
                id,
                "Kitty",
                "Santiago",
                "222",
                null
        );

        // act
        Odontologo odontologoConsultado = odontologoServicio.consultarPorId(id);

        // assert
        assertEquals(odontologoEsperado.toString(), odontologoConsultado.toString());
    }
    @Test
    void testConsultarTodos() {
        // arrange
        Odontologo odontologo = new Odontologo(null,"Antonella","Araque","333",null);
        odontologoServicio.guardar(odontologo);

        // act
        List<Odontologo> listaOdontologos = odontologoServicio.consultarTodos();

        // assert
        assert(!listaOdontologos.isEmpty());
    }
    @Test
    void testModificar() {
        // arrange
        Odontologo odontologo = new Odontologo(null,"Anghelo","Flores","444",null);
        Long id = odontologoServicio.guardar(odontologo).getId();

        Odontologo odontologoConModificaciones = new Odontologo(
                id,
                "Anghelo Modificado",
                "Flores",
                "444",
                null
        );

        // act
        Odontologo odontologoOriginal = odontologoServicio.consultarPorId(id);
        odontologoServicio.modificar(odontologoConModificaciones);
        Odontologo odontologoModificado = odontologoServicio.consultarPorId(id);

        // assert
        assertNotEquals(odontologoOriginal.toString(), odontologoModificado.toString());
    }

    @Test
    void testEliminarPorId() {
        // arrange
        Odontologo odontologo = new Odontologo(null,"Felipe","Mendez","555",null);
        Long id = odontologoServicio.guardar(odontologo).getId();

        int cantidadOdontologosEsperada = odontologoServicio.consultarTodos().size() - 1;

        // act
        odontologoServicio.eliminar(id);
        int cantidadOdontologos = odontologoServicio.consultarTodos().size();

        // assert
        assert(cantidadOdontologosEsperada == cantidadOdontologos && cantidadOdontologosEsperada >= 0);
    }
}