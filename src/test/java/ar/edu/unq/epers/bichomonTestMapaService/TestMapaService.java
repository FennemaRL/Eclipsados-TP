package ar.edu.unq.epers.bichomonTestMapaService;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.security.Guard;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestMapaService {

    private Entrenador esh;
    private Ubicacion guarderia2;
    private Ubicacion guarderia1;

    private HibernateEntrenadorDao dao;
    private HibernateUbicacionDao ubiDao;
    private EntrenadorService entrenadorService;
    private UbicacionService ubicacionService;
    private MapaService mapaService;
    private UbicacionService ubiService;




    @Before
    public void setUp(){
        guarderia1 = new Guarderia("guarderia1");
        guarderia2 = new Guarderia("guarderia2");

        esh = new Entrenador("esh", guarderia1);

        dao = new HibernateEntrenadorDao();

        ubiDao = new HibernateUbicacionDao();
        entrenadorService = new EntrenadorService(dao);

        ubiService = new UbicacionService(ubiDao);
        guarderia2.setCantidadEntrenadores(5);
        ubiService.guardar(guarderia1);

        mapaService = new MapaService(entrenadorService, ubiService);

        ubicacionService = new UbicacionService(new HibernateUbicacionDao());
        mapaService = new MapaService(entrenadorService, ubicacionService);

        entrenadorService.guardar(esh);
        ubicacionService.guardar(guarderia2);



        }
    @After
    public void tearDown(){
        run(()-> dao.clear());
    }

    @Test
    public void al_mover_al_entrenador_de_guarderia1_a_guarderia2_su_ubicacion_actual_pasa_a_ser_guarderia2(){
        mapaService.mover("esh","guarderia2");
        Entrenador entrenador2 = entrenadorService.recuperar("esh");
        assertEquals("guarderia2", entrenador2.getUbicacion().getNombreUbicacion());
    }

    @Test
    public void dojo_con_campeon_historico() throws InterruptedException { //
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Dojo dojo = new Dojo("unqui", pr);
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(2);
        niveles.add(3);
        NivelEntrenador dadorDeNivel = new NivelEntrenador(niveles);
        ExperienciaValor dadorDeExperiencia = new ExperienciaValor();
        Entrenador pepe = new Entrenador("pepe", dojo, dadorDeExperiencia, dadorDeNivel);
        Especie especie = new Especie("Kalu", TipoBicho.AIRE, 1, 2, 0);
        Bicho kalu = new Bicho(especie);
        Entrenador pepe2 = new Entrenador("pepe2", dojo, dadorDeExperiencia, dadorDeNivel);
        Bicho kalu2 = new Bicho(especie);
        kalu2.setEnergia(500);
        pepe2.agregarBichomon(kalu2);
        pepe.agregarBichomon(kalu);
        pepe.duelear();
        Thread.sleep(2000);
        pepe2.duelear();
        entrenadorService.guardar(pepe2);

        assertEquals(kalu2.getId(), mapaService.campeonHistorico("unqui").getId() );
    }
    @Test
    public void dojo_sin_campeon_historico()  { //funca
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Dojo dojo = new Dojo("unqui", pr);
        ubicacionService.guardar(dojo);

        assertNull(mapaService.campeonHistorico("unqui"));

    }
    @Test
    public void guarderia_campeon_historico()  { //funca
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Guarderia dojo = new Guarderia("unqui");
        ubicacionService.guardar(dojo);

        assertNull(mapaService.campeonHistorico("unqui"));

    }
    @Test
    public void una_ubicacion_sabe_cuantos_entrenadores_hay_en_la_misma(){

        assertEquals(mapaService.cantidadEntrenadores("guarderia1"),0);
        assertEquals(mapaService.cantidadEntrenadores("guarderia2"),5);
    }


}
