package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class  EntrenadorTest {

        private Entrenador lukas;
        private Bicho leo;
        private Especie chocoMon;
        private NivelEntrenador nivelEntrenadorGen;
        private ExperienciaValor expGen;
        private Entrenador esh;
        private Entrenador master;
        private Dojo everest;
        private Guarderia creciente;
        private Bicho roko;
        private Bicho riko;
        private List<Integer> niveles;

        @Before
        public void prepare() {

                niveles = new ArrayList<Integer>();
                niveles.add(1);
                niveles.add(100);
                niveles.add(400);
                expGen = new ExperienciaValor(10,10,15);
                nivelEntrenadorGen = new NivelEntrenador(niveles);
                master = new Entrenador("lucas",creciente, expGen, nivelEntrenadorGen);

                chocoMon = new Especie(1,"chocoMon",CHOCOLATE);
                leo = new Bicho(chocoMon);
                leo.setEnergia(20);



                chocoMon.setCondicionesEvolucion(10,0,1, 0);

                creciente = mock(Guarderia.class);

                 esh = new Entrenador("esh",creciente);
                 roko = mock(Bicho.class);
                 riko = mock(Bicho.class);
                 when(riko.getId()).thenReturn(2);
                 when(roko.getId()).thenReturn(3);
                 esh.agregarBichomon(riko);
                 esh.agregarBichomon(roko);
                 lukas = new Entrenador("lucas",creciente, expGen, nivelEntrenadorGen);
                 when(creciente.capturar(lukas)).thenReturn(riko);
                 System.out.println(creciente.capturar(lukas));

        }


        @Test
        public void al_pedir_el_bicho_con_id_3_devuelve_a_roko(){
                when(roko.getId()).thenReturn(3);

                assertEquals(roko, esh.getBichoConID(3));
        }
        @Test
        public void un_entrenador_con_un_experiencia_es_lvl_1(){
                assertEquals(new Integer(1), lukas.getNivel());
        }
        @Test
        public void un_entrenador_con_800_exp_es_lvl3 (){
                for (int i=0 ;i <41 ;i +=1)
                        master.aumentarExpPorCombate();
                assertEquals(new Integer(3), master.getNivel());
        }

        @Test(expected = BichomonError.class)
        public void entrenador_de_nivel_1_no_puede_tener_mas_de_2_bichomones(){
            lukas.capturar();
            when(lukas.getUbicacion().capturar(lukas)).thenReturn(mock(Bicho.class));
            lukas.capturar();
            lukas.capturar();

        }

        @Test (expected = EntrenadorException.class)
        public void al_pedir_el_bicho_con_id_99_devuelve_null(){
                esh.getBichoConID(99);
        }

        @Test
        public void al_agregar_un_bicho_se_setea_el_Owner_de_ese_bicho(){
                verify(roko,atLeast(1)).setOwner(esh);
        }


        @Test (expected = EntrenadorException.class)
        public void el_entrenador_no_puede_abandonar_un_bicho_que_no_posee(){
                esh.abandonarBicho(7);
        }

        @Test (expected = BichomonError.class)
        public void el_entrenador_puede_abandonar_un_bicho_siempre_que_no_se_quede_sin_bichos(){

                esh.abandonarBicho(2);

                verify(riko, atLeast(1)).abandonar();
                esh.abandonarBicho(3);

                assertEquals(1, esh.getBichos().size());
        }

}