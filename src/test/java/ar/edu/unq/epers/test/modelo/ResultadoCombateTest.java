package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Turno;
import org.junit.Test;

import javax.xml.transform.Result;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultadoCombateTest {

    @Test
    public void bicho_pepe_energia_gana_a_bicho_juan_Y_Guarda_Historial() {
        Bicho b = mock(Bicho.class);

        Bicho b2 = mock(Bicho.class);

        when(b.getEnergia()).thenReturn(250);
        when(b2.getEnergia()).thenReturn((1));

        ResultadoCombate resultado = new ResultadoCombate(b,b2);

        assertEquals(b, resultado.getGanador());
        assertFalse(resultado.getTurnos().size()==0);
        List<Turno> turnos =resultado.getTurnos();
        assertEquals(b,turnos.get(0).getBicho());
        assertEquals(b2,turnos.get(1).getBicho());

    }

}