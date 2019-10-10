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
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class TestLeaderBoardService {
    @Test
    public void historial_de_campeones_sin_campeones_ni_dojos(){ // vacio
        HibernateUbicacionDao dao = new HibernateUbicacionDao();
        LeaderBoardService lbs = new LeaderBoardService(dao);


        assertEquals(new ArrayList<Entrenador>(),lbs.campeones());
    }
    @Test
    public void historial_de_campenes_mas_longevo_de_los_dojos() throws InterruptedException {
        HibernateUbicacionDao daou = new HibernateUbicacionDao();
        UbicacionService us = new UbicacionService(daou);
        LeaderBoardService lbs = new LeaderBoardService(daou);

        ProbabilidadNoRandom pn = new ProbabilidadNoRandom();
        Ubicacion dojo = new Dojo("1114", pn);
        Ubicacion dojo1 = new Dojo("1114bis", pn);
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(2);
        niveles.add(3);
        NivelEntrenador dadorDeNivel = new NivelEntrenador(niveles);
        ExperienciaValor dadorDeExperiencia = new ExperienciaValor();

        Especie esp = new Especie("Kame", TipoBicho.TIERRA,10,9,0);

        Bicho b0 = new Bicho(esp);
        Bicho b1 = new Bicho(esp);
        Bicho b2 = new Bicho(esp);
        Bicho b3 = new Bicho(esp);
        Bicho b4 = new Bicho(esp);
        Bicho b5 = new Bicho(esp);
        Bicho b6 = new Bicho(esp);
        Bicho b7 = new Bicho(esp);
        Bicho b8 = new Bicho(esp);
        Bicho b9 = new Bicho(esp);
        Bicho b10 = new Bicho(esp);
        Bicho b11 = new Bicho(esp);
        //dojo1
        b0.setEnergia(1);b1.setEnergia(10);b2.setEnergia(20);b3.setEnergia(40);b4.setEnergia(80);
        b4.setEnergia(160);b5.setEnergia(320);b6.setEnergia(640);b7.setEnergia(1080);b8.setEnergia(2160);b9.setEnergia(4320);
        //dojo2
        b10.setEnergia(1);b11.setEnergia(10);

        Entrenador pepe = new Entrenador("pepe", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe1 = new Entrenador("pepe1", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe2 = new Entrenador("pepe2", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe3 = new Entrenador("pepe3", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe4 = new Entrenador("pepe4", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe5 = new Entrenador("pepe5", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe6 = new Entrenador("pepe6", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe7 = new Entrenador("pepe7", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe8 = new Entrenador("pepe8", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepe9 = new Entrenador("pepe9", dojo, dadorDeExperiencia, dadorDeNivel);
        Entrenador pepa = new Entrenador("pepa", dojo1,dadorDeExperiencia,dadorDeNivel);
        Entrenador pepa1 = new Entrenador("pepa1", dojo1,dadorDeExperiencia,dadorDeNivel);

        pepe.agregarBichomon(b0);
        pepe1.agregarBichomon(b1);
        pepe2.agregarBichomon(b2);
        pepe3.agregarBichomon(b3);
        pepe4.agregarBichomon(b4);
        pepe5.agregarBichomon(b5);
        pepe6.agregarBichomon(b6);
        pepe7.agregarBichomon(b7);
        pepe8.agregarBichomon(b8);
        pepe9.agregarBichomon(b9);
        pepa.agregarBichomon(b10);
        pepa1.agregarBichomon(b11);


        pepe.duelear();
        TimeUnit.SECONDS.sleep(9);
        pepe1.duelear();
        TimeUnit.SECONDS.sleep(8);
        pepe2.duelear();
        TimeUnit.SECONDS.sleep(7);
        pepe3.duelear();
        TimeUnit.SECONDS.sleep(6);
        pepe4.duelear();
        TimeUnit.SECONDS.sleep(5);
        pepe5.duelear();
        TimeUnit.SECONDS.sleep(4);
        pepe6.duelear();
        TimeUnit.SECONDS.sleep(3);
        pepe7.duelear();
        TimeUnit.SECONDS.sleep(2);
        pepe8.duelear();
        TimeUnit.SECONDS.sleep(1);
        pepe9.duelear();

        pepa.duelear();
        TimeUnit.SECONDS.sleep(10);
        pepa1.duelear();

        us.guardar(dojo);
        us.guardar(dojo1);

        List<Entrenador> esperado = new ArrayList<>();

        esperado.add(pepe9);esperado.add(pepa1);esperado.add(pepa);esperado.add(pepe);esperado.add(pepe1);esperado.add(pepe2);esperado.add(pepe3);
        esperado.add(pepe4);esperado.add(pepe5);esperado.add(pepe6);

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
}
