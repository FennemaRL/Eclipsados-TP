package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.GuarderiaErrorNoBichomon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UbicacionTest {
    private RandomBichomon rm ;
/*
    @Before
    public void setUp(){
        rm = mock (RandomBichomon.class);
    }
    @Test(expected = GuarderiaErrorNoBichomon.class)
    public void guarderia_no_tiene_bichomones_y_lanza_una_excepcion_al_capturar(){ //
        Ubicacion guar= new Guarderia("chaparral");
        Entrenador e = mock(Entrenador.class);
        guar.capturar(e);
    }
    @Test(expected = GuarderiaErrorNoBichomon.class)
    public void guarderia_guarderia_adopta_bichomones_y_los_retorna_cuando_se_captura_en_ella(){ //faltan cosas en los mocks
        Bicho a = mock(Bicho.class);
        Bicho b = mock(Bicho.class);
        Ubicacion guar= new Guarderia("chaparral");
        Entrenador e = mock(Entrenador.class);
        Entrenador e1 = mock(Entrenador.class);
        guar.adoptar(a);
        guar.adoptar(b);
        when(a.tuvodueño(e)).thenReturn(false);
        when(b.tuvodueño(e1)).thenReturn(true);

        assertEquals(a,guar.capturar(e));
        guar.capturar(e1);
    }
    @Test(expected = GuarderiaErrorNoBichomon.class)
    public void guarderia_guarderia_adopta_bichomon_y_no_lo_retorna_poque_lo_quiere_capturar_su_anterior_dueño(){
        Bicho a = mock(Bicho.class);
        Bicho b = mock(Bicho.class);
        Ubicacion guar= new Guarderia("chaparral");
        Entrenador e = mock(Entrenador.class);
        guar.adoptar(a);
        guar.adoptar(b);
        when(b.tuvodueño(e)).thenReturn(true);
        when(a.tuvodueño(e)).thenReturn(true);

        guar.capturar(e);
    }
    @Test(expected = DojoSinEntrenador.class)
    public void dojo_no_tiene_campeon_y_lanza_una_excepcion_a_la_hora_de_capturar(){
        Ubicacion dojo= new Dojo("Santelmo",rm);
        Entrenador e = mock(Entrenador.class);
        dojo.capturar(e);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_abandonar_un_poquemon_en_un_dojo(){
        Bicho a = mock(Bicho.class);
        Ubicacion dojo= new Dojo("Santelmo", rm);
        Especie esmock= mock(Especie.class);
        when(a.getEspecie()).thenReturn(esmock);
        when(esmock.getNombre()).thenReturn("papa");
        dojo.adoptar(a);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_abandonar_un_poquemon_en_un_Pueblo(){
        Bicho a = mock(Bicho.class);
        Ubicacion pueblo= new Pueblo("1114", rm);
        Especie esmock= mock(Especie.class);
        when(a.getEspecie()).thenReturn(esmock);
        when(esmock.getNombre()).thenReturn("papa");
        pueblo.adoptar(a);
    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_combatir_en_un_Pueblo(){
        Bicho b = mock(Bicho.class);
        Entrenador e = mock(Entrenador.class);
        Ubicacion pueblo= new Pueblo("1114",rm);
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
        RandomBichomon r = mock(RandomBichomon.class);
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
        verify(esp,times(1)).getEspecieRaiz();
        verify(esp,times(1)).incrementarEnUnBicho();
        assertEquals(esp, b2.getEspecie());
    }
    @Test
    public void se_desafia_a_un_dojo_sin_campeon(){
        Bicho b = mock(Bicho.class);
        RandomBichomon r = mock(RandomBichomon.class);
        Entrenador e = mock(Entrenador.class);
        Especie esp = mock(Especie.class);
        Ubicacion dojo= new Dojo("Santelmo",r);
        when(b.getId()).thenReturn(1);
        when(b.getEspecie()).thenReturn(esp);
        when(e.tieneBichoConId(1)).thenReturn(true);
        when(e.getBichoConID(1)).thenReturn(b);
        when(r.busquedaExitosa(e,dojo)).thenReturn(true);
        when(esp.getEspecieRaiz()).thenReturn(esp);

        assertEquals(dojo.retar(e,b).getGanador(),b);
    }
    @Test
    public void el_desafiante_pasa_a_ser_el_nuevo_campeon(){
        Bicho b = mock(Bicho.class);
        Bicho b2 = mock(Bicho.class);
        RandomBichomon r = mock(RandomBichomon.class);
        Entrenador e = mock(Entrenador.class);
        Entrenador e2 = mock(Entrenador.class);
        Especie esp = mock(Especie.class);
        Ubicacion dojo= new Dojo("Santelmo",r);

        when(b.getId()).thenReturn(1);
        when(b.getEspecie()).thenReturn(esp);
        when(b.getEnergia()).thenReturn(1);
        when(b2.getEnergia()).thenReturn(20);
        when(e.tieneBichoConId(1)).thenReturn(true);
        when(e.getBichoConID(1)).thenReturn(b);
        when(e.getNombre()).thenReturn("entrenador1");

        when(e2.getNombre()).thenReturn("entrenador2");
        when(r.busquedaExitosa(e,dojo)).thenReturn(true);
        when(esp.getEspecieRaiz()).thenReturn(esp);

        dojo.retar(e,b);

        assertEquals(b2,dojo.retar(e2,b2).getGanador());
    }
    @Test(expected =BichomonError.class)
    public void entrenador_campeon_del_dojo_se_desafia_a_si_mismo(){
        Bicho b = mock(Bicho.class);
        RandomBichomon r = mock(RandomBichomon.class);
        Entrenador e = mock(Entrenador.class);
        Especie esp = mock(Especie.class);
        Ubicacion dojo= new Dojo("Santelmo",r);
        when(b.getId()).thenReturn(1);
        when(b.getEspecie()).thenReturn(esp);
        when(e.tieneBichoConId(1)).thenReturn(true);
        when(e.getNombre()).thenReturn("entrenador1");
        when(e.getBichoConID(1)).thenReturn(b);
        when(r.busquedaExitosa(e,dojo)).thenReturn(true);
        when(esp.getEspecieRaiz()).thenReturn(esp);

        dojo.retar(e,b);
        dojo.retar(e,b);
    }
    @Test
    public void al_intentar_capturar_en_un_pueblo_se_captura_un_bicho_de_especie_a(){ // caso feliz
        Entrenador e = mock(Entrenador.class);
        RandomBichomon r = mock(RandomBichomon.class);
        Especie a = mock(Especie.class);
        Especie b = mock(Especie.class);
        List<EspecieConProv> le = new ArrayList<>();
        le.add(new EspecieConProv(a,99));le.add(new EspecieConProv(b,1));
        Ubicacion pueblo = new Pueblo("1114",r,le);
        when(r.busquedaExitosa(e,pueblo)).thenReturn(true);
        Bicho bicho =pueblo.capturar(e);
        verify(a,times(1)).incrementarEnUnBicho();
        assertEquals(bicho.getEspecie(),a);
    }
    @Test (expected = CapturaFallida.class)
    public void al_intentar_capturar_en_un_pueblo_no_se_captura_(){ // caso malo
        Entrenador e = mock(Entrenador.class);
        RandomBichomon r = mock(RandomBichomon.class);
        Especie a = mock(Especie.class);
        Especie b = mock(Especie.class);
        Ubicacion u= mock(Ubicacion.class);
        List<EspecieConProv> le = new ArrayList<>();
        le.add(new EspecieConProv(a,14));le.add(new EspecieConProv(b,86));
        Ubicacion pueblo = new Pueblo("1114",r,le);

        when(r.busquedaExitosa(e,u)).thenReturn(false);
        when(r.especiePorProbabilidad(le)).thenReturn(le.get(0).getEsp());

        Bicho bicho =pueblo.capturar(e);
        verify(r,times(1)).busquedaExitosa(e,u);      //preguntar si usar excepcion o null
        verify(r,times(0)).especiePorProbabilidad(le);
        verify(a,times(0)).incrementarEnUnBicho();
        assertTrue(bicho == null);
    }

    @Test
    public void se_trata_de_abandonar_un_poquemon_en_una_guarderia(){
        Bicho a = mock(Bicho.class);
        Guarderia guarderia= new Guarderia("safe");
        Especie esmock= mock(Especie.class);
        when(a.getEspecie()).thenReturn(esmock);
        when(esmock.getNombre()).thenReturn("papa");
        guarderia.adoptar(a);
        assertEquals(1,guarderia.getBichos().size());
    }
*/
}