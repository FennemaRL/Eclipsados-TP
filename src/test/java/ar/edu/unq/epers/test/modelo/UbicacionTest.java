package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.GuarderiaErrorNoBichomon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBusqueda;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UbicacionTest {

    @Test(expected = GuarderiaErrorNoBichomon.class)
    public void guarderia_no_tiene_bichomones_y_lanza_una_excepcion_al_capturar(){
        Ubicacion guar= new Guarderia("chaparral");
        Entrenador e = mock(Entrenador.class);
        guar.capturar(e);
    }
    @Test
    public void guarderia_guarderia_adopta_bichomones_y_los_retorna_cuando_se_captura_en_ella(){
        Bicho a = mock(Bicho.class);
        Bicho b = mock(Bicho.class);
        Ubicacion guar= new Guarderia("chaparral");
        Entrenador e = mock(Entrenador.class);
        guar.adoptar(a);
        guar.adoptar(b);
        assertEquals(a,guar.capturar(e));
        assertFalse(a.equals(guar.capturar(e)));
    }
    @Test(expected = DojoSinEntrenador.class)
    public void dojo_no_tiene_campeon_y_lanza_una_excepcion_a_la_hora_de_capturar(){
        RandomBusqueda r = mock(RandomBusqueda.class);
        Ubicacion dojo= new Dojo("Santelmo", r);
        Entrenador e = mock(Entrenador.class);
        dojo.capturar(e);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_abandonar_un_poquemon_en_un_dojo(){
        RandomBusqueda r = mock(RandomBusqueda.class);
        Bicho a = mock(Bicho.class);
        Ubicacion dojo= new Dojo("Santelmo",r);
        Especie esmock= mock(Especie.class);
        when(a.getEspecie()).thenReturn(esmock);
        when(esmock.getNombre()).thenReturn("papa");
        dojo.adoptar(a);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_abandonar_un_poquemon_en_un_Pueblo(){
        Bicho a = mock(Bicho.class);
        Ubicacion pueblo= new Pueblo("1114");
        Especie esmock= mock(Especie.class);
        when(a.getEspecie()).thenReturn(esmock);
        when(esmock.getNombre()).thenReturn("papa");
        pueblo.adoptar(a);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_combatir_en_un_Pueblo(){
        Bicho b = mock(Bicho.class);
        Entrenador e = mock(Entrenador.class);
        Ubicacion pueblo= new Pueblo("1114");
        pueblo.retar(e,b);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_combatir_en_una_Guarderia(){
        Bicho b = mock(Bicho.class);
        Entrenador e = mock(Entrenador.class);
        Ubicacion guarderia= new Guarderia("chaparral");
        guarderia.retar(e,b);
    }
    @Test
    public void al_capturar_en_un_dojo_con_campeon(){
        Bicho b = mock(Bicho.class);
        RandomBusqueda r = mock(RandomBusqueda.class);
        Entrenador e = mock(Entrenador.class);
        Especie esp = mock(Especie.class);
        Ubicacion dojo= new Dojo("Santelmo",r);
        when(b.getId()).thenReturn(1);
        when(b.getEspecie()).thenReturn(esp);
        when(e.tieneBichoConId(1)).thenReturn(true);
        when(e.getBichoConID(1)).thenReturn(b);
        when(r.busquedaExitosa(e,dojo)).thenReturn(true);
        when(esp.getEspecieRaiz()).thenReturn(esp);

        dojo.retar(e,b);
        Bicho b2 = dojo.capturar(e);
        verify(r,times (1)).busquedaExitosa(e,dojo);
        verify(esp,times(1)).getEspecieRaiz();
        verify(esp,times(1)).incrementarEnUnBicho();
        assertEquals(esp, b2.getEspecie());
    }
    @Test
    public void al_intentar_capturar_en_un_pueblo_se_captura_un_bicho_de_especie_a(){ // caso feliz
        Entrenador e = mock(Entrenador.class);
        RandomBusqueda r = mock(RandomBusqueda.class);
        Especie a = mock(Especie.class);
        Especie b = mock(Especie.class);
        List<Especie> le = new ArrayList<>();
        le.add(a);le.add(b);
        List ep = new ArrayList<Integer>();
        ep.add(14);ep.add(86);
        Ubicacion pueblo = new Pueblo("1114",r,le,ep);
        when(r.busquedaExitosa(e,pueblo)).thenReturn(true);
        when(r.especiePorProbabilidad(le,ep)).thenReturn(le.get(0));

        Bicho bicho =pueblo.capturar(e);
        verify(r,times(1)).busquedaExitosa(e,pueblo);
        verify(r,times(1)).especiePorProbabilidad(le,ep);
        verify(a,times(1)).incrementarEnUnBicho();
        assertEquals(bicho.getEspecie(),a);
    }
    @Test (expected = CapturaFallida.class)
    public void al_intentar_capturar_en_un_pueblo_no_se_captura_(){ // caso malo
        Entrenador e = mock(Entrenador.class);
        RandomBusqueda r = mock(RandomBusqueda.class);
        Especie a = mock(Especie.class);
        Especie b = mock(Especie.class);
        Ubicacion u= mock(Ubicacion.class);
        List<Especie> le = new ArrayList<>();
        le.add(a);le.add(b);
        List ep = new ArrayList<Integer>();
        ep.add(14);ep.add(86);
        Ubicacion pueblo = new Pueblo("1114",r,le,ep);
        when(r.busquedaExitosa(e,u)).thenReturn(false);
        when(r.especiePorProbabilidad(le,ep)).thenReturn(le.get(0));

        Bicho bicho =pueblo.capturar(e);
      /*  verify(r,times(1)).busquedaExitosa(e,u);      //preguntar si usar excepcion o null
        verify(r,times(0)).especiePorProbabilidad(le,ep);
        verify(a,times(0)).incrementarEnUnBicho();
        assertTrue(bicho == null);
    */}

}