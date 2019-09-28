package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Energia;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Victoria;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BichoTest {
    private Especie rko;
    private Especie rko2;
    private Bicho roko;
    private Entrenador leo;
    private Energia energia;
    private Victoria victoria;


    @Before
    public void setUp() {
        rko = mock(Especie.class);
        rko2 = mock(Especie.class);
        leo = mock(Entrenador.class);
        victoria = mock(Victoria.class);
        energia = mock (Energia.class);
        roko = new Bicho(rko);
        roko.setOwner(leo);
        roko.setFechaCaptura(new Date(2001,10,10));
        roko.setEnergia(50);

        when(energia.cumpleCondicion(leo,roko)).thenReturn(true);
        when(victoria.cumpleCondicion(leo,roko)).thenReturn(true);
        when(leo.getNivel()).thenReturn(10);
        when(rko.evolucionar(true)).thenReturn(rko2);
        roko.agregarCondicion(energia);
        roko.agregarCondicion(victoria);
    }

    @Test
    public void un_bicho_puede_evolucionar_si_cumple_las_condiciones_dadas_por_su_especie(){

        assertTrue(roko.puedeEvolucionar());
    }

    @Test
    public void un_bicho_puede_no_evolucionar_si_no_cumple_las_condiciones_dadas_por_su_especie(){

        when(victoria.cumpleCondicion(leo,roko)).thenReturn(false);
        assertFalse(roko.puedeEvolucionar());
    }

    @Test
    public void un_bicho_evoluciona_si_cumple_las_condiciones_dadas_por_su_especie(){
        roko.evolucionar();
        assertEquals(roko.getEspecie(), rko2);
    }

}
