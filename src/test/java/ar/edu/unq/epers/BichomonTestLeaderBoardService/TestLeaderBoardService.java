package ar.edu.unq.epers.BichomonTestLeaderBoardService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;
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
    private ProbabilidadNoRandom pn;

    @Before
    public void setup(){
         daou = new HibernateUbicacionDao();
         us = new UbicacionService(daou);
        lbs = new LeaderBoardService(daou);


        pn = new ProbabilidadNoRandom();
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
        Ubicacion dojo10= new Dojo("dojo3",pn);
        Ubicacion dojo2 = new Dojo("dojo4",pn);
        Ubicacion dojo3 = new Dojo("dojo5",pn);
        Ubicacion dojo4 = new Dojo("dojo6",pn);
        Ubicacion dojo5 = new Dojo("dojo7",pn);
        Ubicacion dojo6 = new Dojo("dojo8",pn);
        Ubicacion dojo7 = new Dojo("dojo9",pn);
        Ubicacion dojo8 = new Dojo("dojo10",pn);
        Ubicacion dojo9 = new Dojo("dojo11",pn);

        Entrenador pepe1 = new Entrenador("pepe1", dojo10, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe2 = new Entrenador("pepe2", dojo2, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe3 = new Entrenador("pepe3", dojo3, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe4 = new Entrenador("pepe4", dojo4, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe5 = new Entrenador("pepe5", dojo5, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe6 = new Entrenador("pepe6", dojo6, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe7 = new Entrenador("pepe7", dojo7, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe8 = new Entrenador("pepe8", dojo8, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe9 = new Entrenador("pepe9", dojo9, dadorDeExperiencia, dadorDeNivel);


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
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepe9.duelear();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pepa.duelear();

        pepa1.duelear();
        us.guardar(dojo);
        us.guardar(dojo1);
        us.guardar(dojo2);
        us.guardar(dojo3);
        us.guardar(dojo4);
        us.guardar(dojo5);
        us.guardar(dojo6);
        us.guardar(dojo7);
        us.guardar(dojo8);
        us.guardar(dojo9);
        us.guardar(dojo10);

        List<Entrenador> esperado = new ArrayList<>();

        esperado.add(pepe);
        esperado.add(pepe1);
        esperado.add(pepe2);
        esperado.add(pepe3);
        esperado.add(pepe4);
        esperado.add(pepe5);
        esperado.add(pepe6);
        esperado.add(pepe7);
        esperado.add(pepe8);
        esperado.add(pepe9);

        List<Entrenador> resultante = lbs.campeones();
        assertEquals(esperado, resultante);

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
    @Test
    public void lideres_retorna_una_lista_vacia_ya_que_no_hay_entrenadores_con_bichomones_o_entrenadores(){
        assertEquals(new ArrayList<Entrenador>(),lbs.lideres());
    }
    @Test
    public void lideres_retorna_los_10_de_11_entrenadores_con_mas_valor_de_poder(){
        HibernateEntrenadorDao dao = new HibernateEntrenadorDao();
        EntrenadorService service = new EntrenadorService(dao);

        Bicho b  = new Bicho(esp);        Bicho b0 = new Bicho(esp);
        Entrenador npi = new Entrenador ("npi",dojo);
        npi.agregarBichomon(b);npi.agregarBichomon(b0);b.setEnergia(1);b0.setEnergia(1);

        Bicho b1 = new Bicho(esp);        Bicho b3 = new Bicho(esp);
        Entrenador npi1 = new Entrenador ("npi1",dojo);
        npi1.agregarBichomon(b1);npi1.agregarBichomon(b3);b1.setEnergia(1);b3.setEnergia(2);

        Bicho b4 = new Bicho(esp);        Bicho b5 = new Bicho(esp);
        Entrenador npi2 = new Entrenador ("npi2",dojo);
        npi2.agregarBichomon(b4);npi2.agregarBichomon(b5);b4.setEnergia(2);b5.setEnergia(2);

        Bicho b6  = new Bicho(esp);        Bicho b7 = new Bicho(esp);
        Entrenador npi3 = new Entrenador ("npi3",dojo);
        npi3.agregarBichomon(b6);npi3.agregarBichomon(b7);b6.setEnergia(3);b7.setEnergia(2);

        Bicho b8  = new Bicho(esp);        Bicho b9  = new Bicho(esp);
        Entrenador npi4 = new Entrenador ("npi4",dojo);
        npi4.agregarBichomon(b8);npi4.agregarBichomon(b9);b8.setEnergia(3);b9.setEnergia(3);

        Bicho b10  = new Bicho(esp);        Bicho b11  = new Bicho(esp);
        Entrenador npi5 = new Entrenador ("npi5",dojo);
        npi5.agregarBichomon(b10);npi5.agregarBichomon(b11);b10.setEnergia(4);b11.setEnergia(3);

        Bicho b12  = new Bicho(esp);        Bicho b13  = new Bicho(esp);
        Entrenador npi6 = new Entrenador ("npi6",dojo);
        npi6.agregarBichomon(b12);npi6.agregarBichomon(b13);b12.setEnergia(4);b13.setEnergia(4);

        Bicho b14  = new Bicho(esp);        Bicho b15  = new Bicho(esp);
        Entrenador npi7 = new Entrenador ("npi7",dojo);
        npi7.agregarBichomon(b14);npi7.agregarBichomon(b15);b14.setEnergia(5);b15.setEnergia(4);

        Bicho b16  = new Bicho(esp);        Bicho b17  = new Bicho(esp);
        Entrenador npi8 = new Entrenador ("npi8",dojo);
        npi8.agregarBichomon(b16);npi8.agregarBichomon(b17);b16.setEnergia(5);b17.setEnergia(5);

        Bicho b18  = new Bicho(esp);        Bicho b19  = new Bicho(esp);
        Entrenador npi9 = new Entrenador ("npi9",dojo);
        npi9.agregarBichomon(b18);npi9.agregarBichomon(b19);b18.setEnergia(6);b19.setEnergia(5);

        Bicho b20  = new Bicho(esp);        Bicho b21  = new Bicho(esp);
        Entrenador npi10 = new Entrenador ("np10",dojo);
        npi10.agregarBichomon(b20);npi10.agregarBichomon(b21);b20.setEnergia(6);b21.setEnergia(6);




        service.guardar(npi);
        service.guardar(npi1);
        service.guardar(npi2);
        service.guardar(npi3);
        service.guardar(npi4);
        service.guardar(npi5);
        service.guardar(npi6);
        service.guardar(npi7);
        service.guardar(npi8);
        service.guardar(npi9);
        service.guardar(npi10);

        List<Entrenador> esperado = new ArrayList<>();
        esperado.add(npi10);
        esperado.add(npi9);
        esperado.add(npi8);
        esperado.add(npi7);
        esperado.add(npi6);
        esperado.add(npi5);
        esperado.add(npi4);
        esperado.add(npi3);
        esperado.add(npi2);
        esperado.add(npi1);
        assertEquals(esperado, lbs.lideres());
    }


}
