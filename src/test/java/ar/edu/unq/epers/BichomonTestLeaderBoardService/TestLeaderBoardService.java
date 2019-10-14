package ar.edu.unq.epers.BichomonTestLeaderBoardService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.LeaderBoardService;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieNoExistente;
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class TestLeaderBoardService {


    private LeaderBoardService lbs;
    private HibernateUbicacionDao daou;
    private UbicacionService us;
    private Entrenador pepe;
    private Entrenador pepa;
    private Entrenador pepa1;
    private Ubicacion dojo;
    private Ubicacion dojo1;
    private NivelEntrenador dadorDeNivel;
    private ExperienciaValor dadorDeExperiencia;
    private Bicho b0;
    private Bicho b10;
    private Bicho b11;
    private Especie esp;
    private Especie esp1;

    @Before
    public void setup(){
         daou = new HibernateUbicacionDao();
         us = new UbicacionService(daou);
        lbs = new LeaderBoardService(daou);


        ProbabilidadNoRandom pn = new ProbabilidadNoRandom();
        dojo = new Dojo("1114", pn);
        dojo1 = new Dojo("1114bis", pn);
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(2);
        niveles.add(3);
        dadorDeNivel = new NivelEntrenador(niveles);
        dadorDeExperiencia = new ExperienciaValor();

        esp = new Especie("Kame", TipoBicho.TIERRA, 10, 9, 0);
        esp1 = new Especie("KamePrima", TipoBicho.TIERRA, 10, 9, 0);

        b0 = new Bicho(esp);
        b10 = new Bicho(esp1);
        b11 = new Bicho(esp1);
        //dojo1
        b0.setEnergia(1);
        //dojo2
        b10.setEnergia(1);
        b11.setEnergia(10);

         pepe = new Entrenador("pepe", dojo, dadorDeExperiencia, dadorDeNivel);
         pepa = new Entrenador("pepa", dojo1, dadorDeExperiencia, dadorDeNivel);
         pepa1 = new Entrenador("pepa1", dojo1, dadorDeExperiencia, dadorDeNivel);

        pepe.agregarBichomon(b0);
        pepa.agregarBichomon(b10);
        pepa1.agregarBichomon(b11);

    }
    @After
    public void tear(){
        run(()->daou.clear());
    }
    @Test
    public void historial_de_campeones_sin_campeones_ni_dojos() { // test campeones

        assertEquals(new ArrayList<Entrenador>(), lbs.campeones());
    }


    @Test
    public void historial_de_campenes_mas_longevo_de_los_dojos_habiendo_mas_de_diez_entrenadores_que_combatieron_en_estos_dojos() { //test campeones

        Bicho b1 = new Bicho(esp);
        Bicho b2 = new Bicho(esp);
        Bicho b3 = new Bicho(esp);
        Bicho b4 = new Bicho(esp);
        Bicho b5 = new Bicho(esp);
        Bicho b6 = new Bicho(esp);
        Bicho b7 = new Bicho(esp);
        Bicho b8 = new Bicho(esp);
        Bicho b9 = new Bicho(esp1);
        //dojo1

        b1.setEnergia(10);
        b2.setEnergia(20);
        b3.setEnergia(40);
        b4.setEnergia(80);
        b4.setEnergia(160);
        b5.setEnergia(320);
        b6.setEnergia(640);
        b7.setEnergia(1080);
        b8.setEnergia(2160);
        b9.setEnergia(4320);
        //dojo2


        Entrenador pepe1 = new Entrenador("pepe1", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe2 = new Entrenador("pepe2", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe3 = new Entrenador("pepe3", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe4 = new Entrenador("pepe4", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe5 = new Entrenador("pepe5", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe6 = new Entrenador("pepe6", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe7 = new Entrenador("pepe7", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe8 = new Entrenador("pepe8", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe9 = new Entrenador("pepe9", dojo, dadorDeExperiencia, dadorDeNivel);


        pepe1.agregarBichomon(b1);
        pepe2.agregarBichomon(b2);
        pepe3.agregarBichomon(b3);
        pepe4.agregarBichomon(b4);
        pepe5.agregarBichomon(b5);
        pepe6.agregarBichomon(b6);
        pepe7.agregarBichomon(b7);
        pepe8.agregarBichomon(b8);
        pepe9.agregarBichomon(b9);


        pepe.duelear();
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe1.duelear();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe2.duelear();
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe3.duelear();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe4.duelear();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe5.duelear();
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe6.duelear();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe7.duelear();
        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe8.duelear();
        pepe9.duelear();
        pepa.duelear();

        pepa1.duelear();
        us.guardar(dojo);
        us.guardar(dojo1);

        List<Entrenador> esperado = new ArrayList<>();

        esperado.add(pepe9);
        esperado.add(pepa1);
        esperado.add(pepe);
        esperado.add(pepe1);
        esperado.add(pepe2);
        esperado.add(pepe3);
        esperado.add(pepe4);
        esperado.add(pepe5);
        esperado.add(pepe6);
        esperado.add(pepe7);


        List<Entrenador> resultante = lbs.campeones();
        assertEquals(esperado.get(0).getNombre(), resultante.get(0).getNombre());
        assertEquals(esperado.get(1).getNombre(), resultante.get(1).getNombre());
        assertEquals(esperado.get(2).getNombre(), resultante.get(2).getNombre());
        assertEquals(esperado.get(3).getNombre(), resultante.get(3).getNombre());
        assertEquals(esperado.get(4).getNombre(), resultante.get(4).getNombre());
        assertEquals(esperado.get(5).getNombre(), resultante.get(5).getNombre());
        assertEquals(esperado.get(6).getNombre(), resultante.get(6).getNombre());
        assertEquals(esperado.get(7).getNombre(), resultante.get(7).getNombre());
        assertEquals(esperado.get(8).getNombre(), resultante.get(8).getNombre());
        assertEquals(esperado.get(9).getNombre(), resultante.get(9).getNombre());

    }

    @Test (expected = EspecieNoExistente.class)
    public void especieLider_sin_dojos_ni_entrenadores(){
        lbs.especieLider();
    }

    @Test
    public void especieLider_con_dojos_y_entrenadores() { //test especieLider



        pepe.duelear();
        pepa.duelear();
        pepa1.duelear();

        us.guardar(dojo);
        us.guardar(dojo1);

        assertEquals(esp1.getNombre(),lbs.especieLider().getNombre());

    }

    @Test
    public void especieLider_con_dojos_y_entrenadores_repetidos()  { //test especieLider donde un mismo entrenador gana varias veces

        //revisar por que no pierde
        Bicho b1 = new Bicho(esp);
        Bicho b2 = new Bicho(esp);


        Entrenador pepe2 = new Entrenador("pepe2", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe3 = new Entrenador("pepe3", dojo, dadorDeExperiencia, dadorDeNivel);


        pepe2.agregarBichomon(b1);
        pepe3.agregarBichomon(b2);
        b1.setEnergia(40);
        b2.setEnergia(80);
        pepe.duelear();
        pepe2.duelear();
        pepe3.duelear();

        b10.setEnergia(1);
        pepa.duelear();
        b11.setEnergia(10);
        pepa1.duelear();
        b10.setEnergia(100);
        pepa.duelear();

        us.guardar(dojo);
        us.guardar(dojo1);

        assertEquals(esp.getNombre(),lbs.especieLider().getNombre());

    }


}
