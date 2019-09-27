package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ZonaErronea;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class  EntrenadorTest {

        private Entrenador lukas;
        private Bicho leo;
        private Especie chocoMon;


        private Entrenador esh;
        private Dojo everest;
        private Guarderia creciente;
        private Bicho roko;
        private Bicho riko;

        @Before
        public void prepare() {
                lukas = new Entrenador( 1, "Lukas", 1, 1);
                chocoMon = new Especie(1,"chocoMon",CHOCOLATE);
                leo = new Bicho(chocoMon);
                leo.setEnergia(20);
                lukas.agregarBichomon(leo);
                //chocoMon.setCondicionesEvolucion(10,0,1, DateTime.parse("2020-11-10"));

                creciente = mock(Guarderia.class);
                everest = new Dojo();
                esh = new Entrenador("esh",creciente);
                roko = new Bicho(chocoMon);
                riko = new Bicho(chocoMon);
                esh.agregarBichomon(riko);
                esh.agregarBichomon(roko);
        }


        @Test
        public void un_entrenador_tiene_un_bichomon() {

                assertEquals(lukas.getBichoConID(leo.getId()),leo);
        }
        /*
        @Test
        public void el_entrenador_sabe_si_su_bichomon_puede_evolucionar(){

                assertTrue(lukas.puedeEvolucionarBichoConID(1));
        }

        @Test
        public  void el_entrenador_sabe_si_puede_evolucionar_a_un_bichomon_por_los_requisitos_de_su_especie(){
                chocoMon.setCondicionesEvolucion(30,1,10,DateTime.parse("2015-11-10"));
                assertFalse(lukas.puedeEvolucionarBichoConID(1));
        }

        @Test (expected = EntrenadorException.class)
        public void el_entrenador_no_puede_acceder_a_bichos_que_no_posee(){
                lukas.puedeEvolucionarBichoConID(2);
        }


         */

        @Test
        public void el_entrenador_puede_abandonar_un_bicho_siempre_que_no_se_quede_sin_bichos(){
                esh.abandonarBicho(riko.getId());

                esh.abandonarBicho(roko.getId());

                assertEquals(1, esh.getBichos().size());
        }

        @Test (expected = ZonaErronea.class)
        public void al_abandonar_un_bicho_en_una_zona_incorrecta_levanta_una_excepcion_y_el_bicho_no_se_abandona(){
                esh.setUbicacion(everest);

                assertEquals(2, esh.getBichos().size());

                esh.abandonarBicho(roko.getId());

                assertEquals(2, esh.getBichos().size());
        }

}



