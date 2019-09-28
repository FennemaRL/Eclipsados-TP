package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EspecieNoPuedeEvolucionar;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EspecieTest {
    private static final TipoBicho CHOCOLATE = TipoBicho.CHOCOLATE;
    private static final TipoBicho ELECTRICIDAD = TipoBicho.ELECTRICIDAD;
    private Especie leomon;
    private Especie chacomon;
    private Bicho diego;
    private DateTime fechaCaptura;

    @Before
    public void setUp(){
        leomon = new Especie("leomon",CHOCOLATE,10,20,1);
        chacomon = new Especie("chacomon",ELECTRICIDAD,10,20,1);
        leomon.setEspecieEvo(chacomon);
        fechaCaptura = DateTime.parse("2020-11-10");

        diego = mock(Bicho.class);
        when(diego.getEnergia()).thenReturn(100);
        when(diego.getVictorias()).thenReturn(10);
        when(diego.getNivelEntrenador()).thenReturn(5);
        when(diego.getFechaCaptura()).thenReturn((long) 12345);
    }

    @Test
    public void una_especie_sabe_si_un_bicho_puede_evolucionar_a_una_siguiente_especie_si_este_cumple_todas_las_condiciones(){


        leomon.setCondicionesEvolucion(50,5,2,0);

        assertTrue(leomon.cumpleCondicion(true));

    }

    @Test
    public void una_especie_sabe_si_no_es_capaz_evolucionar_a_una_siguiente_especie_si_este_no_cumple_todas_las_condiciones(){


        leomon.setCondicionesEvolucion(101,11,6,12034);

        assertFalse(leomon.cumpleCondicion(false));

    }
    @Test
    public void una_especie_evoluciona_en_otra_si_este_cumple_todas_las_condiciones(){


        leomon.setCondicionesEvolucion(50,5,2,0);


        assertEquals(leomon.evolucionar(true),chacomon);

    }

    @Test (expected = EspecieNoPuedeEvolucionar.class)
    public void una_especie_rompe_si_no_puede_evolucionar(){


        leomon.setCondicionesEvolucion(50,5,2,0);


        assertEquals(leomon.evolucionar(false),chacomon);

    }

}
