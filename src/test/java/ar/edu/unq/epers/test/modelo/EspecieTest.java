package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EspecieNoPuedeEvolucionar;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.ELECTRICIDAD;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EspecieTest {
    private Especie leomon;
    private Especie chacomon;
    private Bicho teco;
    private Entrenador timmy;

    @Before
    public void setUp(){
        leomon = new Especie("leomon",CHOCOLATE,10,20,1);
        chacomon = new Especie("chacomon",ELECTRICIDAD,10,20,1);
        leomon.setEspecieEvo(chacomon);
        teco = mock(Bicho.class);
        timmy = mock(Entrenador.class);
    }

    @Test(expected = EspecieNoPuedeEvolucionar.class)
    public void especie_no_puede_evolucionar_por_que_no_tiene_evolucion(){
        assertFalse(chacomon.puedeEvolucionar(teco,timmy));
        chacomon.evolucionar(teco,timmy);
    }
    @Test
    public void especie_evoluciona_en_otra_especie(){
        assertTrue(leomon.puedeEvolucionar(teco,timmy));
        assertEquals(chacomon, leomon.evolucionar(teco,timmy));
    }
    @Test(expected = EspecieNoPuedeEvolucionar.class)
    public void especie_no_puede_evolucionar_por_que_no_cumple_condicion(){
        Condicion mcond = mock(Condicion.class);
        leomon.nuevaCondicion(mcond);
        when(mcond.cumpleCondicion(timmy,teco)).thenReturn(false);
        assertFalse(leomon.puedeEvolucionar(teco,timmy));
        leomon.evolucionar(teco,timmy);
    }
    @Test
    public void especie_evoluciona_en_otra_especie_ya_que_cumple_las_condiciones_de_evo(){
        Condicion mcond = mock(Condicion.class);
        leomon.nuevaCondicion(mcond);
        when(mcond.cumpleCondicion(timmy,teco)).thenReturn(true);
        assertTrue(leomon.puedeEvolucionar(teco,timmy));
        assertEquals(chacomon, leomon.evolucionar(teco,timmy));
    }

}
