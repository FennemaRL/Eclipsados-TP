package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static org.junit.Assert.*;

public class  EntrenadorTest {

        private Entrenador lukas;
        private Bicho leo;
        private Especie chocoMon;

        @Before
        public void prepare() {
                lukas = new Entrenador( 1, "Lukas", 1, 1);
                chocoMon = new Especie(1,"chocoMon",CHOCOLATE);
                leo = new Bicho(chocoMon);
                leo.setEnergia(20);
                lukas.agregarBichomon(leo);
                chocoMon.setCondicionesEvolucion(10,0,1, DateTime.parse("2020-11-10"));
        }


        @Test
        public void un_entrenador_tiene_un_bichomon() {

                assertEquals(lukas.getBichoConID(1),leo);
        }
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
}



