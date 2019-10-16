package ar.edu.unq.epers.bichomonTestEspecieService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.EspecieServiceh;
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static junit.framework.TestCase.assertEquals;

public class TestEspecieService {
    private HibernateEntrenadorDao daoe;
    private EntrenadorService entrenadorS;
    private EspecieServiceh especieService;
    private Dojo dojo;

    @Before
    public void setup(){
        daoe = new HibernateEntrenadorDao();
        entrenadorS = new EntrenadorService(daoe);
        HibernateEspecieDao daoesp = new HibernateEspecieDao();
        especieService = new EspecieServiceh(daoesp);
        dojo = new Dojo("qq", new ProbabilidadNoRandom());
    }
    @After
    public void tear(){
        run(()->daoe.clear());
    }

    @Test
    public void populares_retorna_las_10_de_11_especies_mas_populares_entre_los_entrenadores(){
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(1);
        niveles.add(2);
        niveles.add(3);
        niveles.add(10);
        niveles.add(11);
        NivelEntrenador dadorDeNivel = new NivelEntrenador(niveles);
        ExperienciaValor dadorDeExperiencia = new ExperienciaValor(10,10,10);

        Entrenador pepe = new Entrenador("pepe0", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe1 = new Entrenador("pepe1", dojo, dadorDeExperiencia ,dadorDeNivel);
        Entrenador pepe2 = new Entrenador("pepe2", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe3 = new Entrenador("pepe3", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe4 = new Entrenador("pepe4", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe5 = new Entrenador("pepe5", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe6 = new Entrenador("pepe6", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe7 = new Entrenador("pepe7", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe8 = new Entrenador("pepe8", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe9 = new Entrenador("pepe9", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe10 = new Entrenador("pepe10", dojo, dadorDeExperiencia,dadorDeNivel);
        Entrenador pepe11 = new Entrenador("pepe11", dojo, dadorDeExperiencia,dadorDeNivel);

        Especie esp = new Especie("Esp", TipoBicho.AIRE,0,0,0);
        Especie esp1 = new Especie("Esp1", TipoBicho.AIRE,0,0,0);
        Especie esp2 = new Especie("Esp2", TipoBicho.AIRE,0,0,0);
        Especie esp3 = new Especie("Esp3", TipoBicho.AIRE,0,0,0);
        Especie esp4 = new Especie("Esp4", TipoBicho.AIRE,0,0,0);
        Especie esp5 = new Especie("Esp5", TipoBicho.AIRE,0,0,0);
        Especie esp6 = new Especie("Esp6", TipoBicho.AIRE,0,0,0);
        Especie esp7 = new Especie("Esp7", TipoBicho.AIRE,0,0,0);
        Especie esp8 = new Especie("Esp8", TipoBicho.AIRE,0,0,0);
        Especie esp9 = new Especie("Esp9", TipoBicho.AIRE,0,0,0);
        Especie esp10 = new Especie("Esp10", TipoBicho.AIRE,0,0,0);
        Especie esp11 = new Especie("Esp11", TipoBicho.AIRE,0,0,0);

        Bicho bEsp = new Bicho(esp);

        Bicho b1Esp1 = new Bicho(esp1);
        Bicho b2Esp1 = new Bicho(esp1);

        Bicho b1Esp2 = new Bicho(esp2);
        Bicho b2Esp2 = new Bicho(esp2);
        Bicho b3Esp2 = new Bicho(esp2);

        Bicho b1Esp3 = new Bicho(esp3);
        Bicho b2Esp3 = new Bicho(esp3);
        Bicho b3Esp3 = new Bicho(esp3);
        Bicho b4Esp3 = new Bicho(esp3);

        Bicho b1Esp4 = new Bicho(esp4);
        Bicho b2Esp4 = new Bicho(esp4);
        Bicho b3Esp4 = new Bicho(esp4);
        Bicho b4Esp4 = new Bicho(esp4);
        Bicho b5Esp4 = new Bicho(esp4);

        Bicho b1Esp5 = new Bicho(esp5);
        Bicho b2Esp5 = new Bicho(esp5);
        Bicho b3Esp5 = new Bicho(esp5);
        Bicho b4Esp5 = new Bicho(esp5);
        Bicho b5Esp5 = new Bicho(esp5);
        Bicho b6Esp5 = new Bicho(esp5);

        Bicho b1Esp6 = new Bicho(esp6);
        Bicho b2Esp6 = new Bicho(esp6);
        Bicho b3Esp6 = new Bicho(esp6);
        Bicho b4Esp6 = new Bicho(esp6);
        Bicho b5Esp6 = new Bicho(esp6);
        Bicho b6Esp6 = new Bicho(esp6);
        Bicho b7Esp6 = new Bicho(esp6);

        Bicho b1Esp7 = new Bicho(esp7);
        Bicho b2Esp7 = new Bicho(esp7);
        Bicho b3Esp7 = new Bicho(esp7);
        Bicho b4Esp7 = new Bicho(esp7);
        Bicho b5Esp7 = new Bicho(esp7);
        Bicho b6Esp7 = new Bicho(esp7);
        Bicho b7Esp7 = new Bicho(esp7);
        Bicho b8Esp7 = new Bicho(esp7);

        Bicho b1Esp8 = new Bicho(esp8);
        Bicho b2Esp8 = new Bicho(esp8);
        Bicho b3Esp8 = new Bicho(esp8);
        Bicho b4Esp8 = new Bicho(esp8);
        Bicho b5Esp8 = new Bicho(esp8);
        Bicho b6Esp8 = new Bicho(esp8);
        Bicho b7Esp8 = new Bicho(esp8);
        Bicho b8Esp8 = new Bicho(esp8);
        Bicho b9Esp8 = new Bicho(esp8);

        Bicho b1Esp9 = new Bicho(esp9);
        Bicho b2Esp9 = new Bicho(esp9);
        Bicho b3Esp9 = new Bicho(esp9);
        Bicho b4Esp9 = new Bicho(esp9);
        Bicho b5Esp9 = new Bicho(esp9);
        Bicho b6Esp9 = new Bicho(esp9);
        Bicho b7Esp9 = new Bicho(esp9);
        Bicho b8Esp9 = new Bicho(esp9);
        Bicho b9Esp9 = new Bicho(esp9);
        Bicho b10Esp9 = new Bicho(esp9);

        Bicho b1Esp10 = new Bicho(esp10);
        Bicho b2Esp10 = new Bicho(esp10);
        Bicho b3Esp10 = new Bicho(esp10);
        Bicho b4Esp10 = new Bicho(esp10);
        Bicho b5Esp10 = new Bicho(esp10);
        Bicho b6Esp10 = new Bicho(esp10);
        Bicho b7Esp10 = new Bicho(esp10);
        Bicho b8Esp10 = new Bicho(esp10);
        Bicho b9Esp10 = new Bicho(esp10);
        Bicho b10Esp10 = new Bicho(esp10);
        Bicho b11Esp10 = new Bicho(esp10);
        Bicho b12Esp10 = new Bicho(esp10);

        Bicho b1Esp11 = new Bicho(esp11);
        Bicho b2Esp11 = new Bicho(esp11);

        pepe.agregarBichomon(bEsp);
        pepe.agregarBichomon(b1Esp1);
        pepe.agregarBichomon(b1Esp2);
        pepe.agregarBichomon(b1Esp3);
        pepe.agregarBichomon(b1Esp4);
        pepe.agregarBichomon(b1Esp5);
        pepe.agregarBichomon(b1Esp6);
        pepe.agregarBichomon(b1Esp7);
        pepe.agregarBichomon(b1Esp8);
        pepe.agregarBichomon(b1Esp9);
        pepe.agregarBichomon(b1Esp10);


        pepe1.agregarBichomon(b2Esp1);
        pepe1.agregarBichomon(b2Esp2);
        pepe1.agregarBichomon(b2Esp3);
        pepe1.agregarBichomon(b2Esp4);
        pepe1.agregarBichomon(b2Esp5);
        pepe1.agregarBichomon(b2Esp6);
        pepe1.agregarBichomon(b2Esp7);
        pepe1.agregarBichomon(b2Esp8);
        pepe1.agregarBichomon(b2Esp9);
        pepe1.agregarBichomon(b2Esp10);

        pepe2.agregarBichomon(b3Esp2);
        pepe2.agregarBichomon(b3Esp3);
        pepe2.agregarBichomon(b3Esp4);
        pepe2.agregarBichomon(b3Esp5);
        pepe2.agregarBichomon(b3Esp6);
        pepe2.agregarBichomon(b3Esp7);
        pepe2.agregarBichomon(b3Esp8);
        pepe2.agregarBichomon(b3Esp9);
        pepe2.agregarBichomon(b3Esp10);


        pepe3.agregarBichomon(b4Esp3);
        pepe3.agregarBichomon(b4Esp4);
        pepe3.agregarBichomon(b4Esp5);
        pepe3.agregarBichomon(b4Esp6);
        pepe3.agregarBichomon(b4Esp7);
        pepe3.agregarBichomon(b4Esp8);
        pepe3.agregarBichomon(b4Esp9);
        pepe3.agregarBichomon(b4Esp10);


        pepe4.agregarBichomon(b5Esp4);
        pepe4.agregarBichomon(b5Esp5);
        pepe4.agregarBichomon(b5Esp6);
        pepe4.agregarBichomon(b5Esp7);
        pepe4.agregarBichomon(b5Esp8);
        pepe4.agregarBichomon(b5Esp9);
        pepe4.agregarBichomon(b5Esp10);



        pepe5.agregarBichomon(b6Esp5);
        pepe5.agregarBichomon(b6Esp6);
        pepe5.agregarBichomon(b6Esp7);
        pepe5.agregarBichomon(b6Esp8);
        pepe5.agregarBichomon(b6Esp9);
        pepe5.agregarBichomon(b6Esp10);


        pepe6.agregarBichomon(b7Esp6);
        pepe6.agregarBichomon(b7Esp7);
        pepe6.agregarBichomon(b7Esp8);
        pepe6.agregarBichomon(b7Esp9);
        pepe6.agregarBichomon(b7Esp10);


        pepe7.agregarBichomon(b8Esp7);
        pepe7.agregarBichomon(b8Esp8);
        pepe7.agregarBichomon(b8Esp9);
        pepe7.agregarBichomon(b8Esp10);

        pepe8.agregarBichomon(b9Esp8);
        pepe8.agregarBichomon(b9Esp9);
        pepe8.agregarBichomon(b9Esp10);

        pepe9.agregarBichomon(b10Esp9);
        pepe9.agregarBichomon(b10Esp10);

        pepe10.agregarBichomon(b11Esp10);


        pepe11.agregarBichomon(b1Esp11);
        pepe11.agregarBichomon(b12Esp10);
        pepe11.agregarBichomon(b2Esp11);

        List<Especie> esperado =new ArrayList<>();

        esperado.add(esp10);
        esperado.add(esp9);
        esperado.add(esp8);
        esperado.add(esp7);
        esperado.add(esp6);
        esperado.add(esp5);
        esperado.add(esp4);
        esperado.add(esp3);
        esperado.add(esp2);
        esperado.add(esp11);

        entrenadorS.guardar(pepe);
        entrenadorS.guardar(pepe1);
        entrenadorS.guardar(pepe2);
        entrenadorS.guardar(pepe3);
        entrenadorS.guardar(pepe4);
        entrenadorS.guardar(pepe5);
        entrenadorS.guardar(pepe6);
        entrenadorS.guardar(pepe7);
        entrenadorS.guardar(pepe8);
        entrenadorS.guardar(pepe9);
        entrenadorS.guardar(pepe10);
        entrenadorS.guardar(pepe11);


        assertEquals(esperado, especieService.populares());
    }
    @Test
    public void populares_retorna_una_lista_vacia_cuando_todos_los_entrenadores_no_tienen_bichomones(){
        assertEquals(new ArrayList<Especie>(), especieService.populares());
    }
}
