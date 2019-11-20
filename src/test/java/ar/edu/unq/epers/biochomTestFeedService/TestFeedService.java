package ar.edu.unq.epers.biochomTestFeedService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB.MongoEventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionNeoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.service.*;
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;
import static org.junit.Assert.assertEquals;
public class TestFeedService {

    private HibernateEntrenadorDao entd;
    private UbicacionNeoDao neodao;
    private UbicacionService ubs;
    private HibernateUbicacionDao daou;
    private MongoEventoDAO mongo;

    @Before
    public void setUp(){

    }
    @After
    public void teardown(){

    }

    @Test
    public void feedUbicaciontest(){
        entd =new HibernateEntrenadorDao();
        daou =new HibernateUbicacionDao();
        ubs = new UbicacionService(daou);
        neodao = new UbicacionNeoDao(ubs);
        mongo = new MongoEventoDAO();
        EntrenadorService entservice = new EntrenadorService(entd);



        MapaService ms = new MapaService(entservice,ubs,neodao);
        BichoService bs = new BichoService(entservice);
        List<Observado> obs = new ArrayList<>();
        obs.add(ms);obs.add(bs);
        FeedService fs = new FeedService(mongo,obs,neodao,entservice);
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Dojo dojo = new Dojo("unqui", pr);
        Guarderia guarderia = new Guarderia("chaparral");
        Guarderia guarderia1 = new Guarderia("sociales");
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(2);
        niveles.add(3);
        NivelEntrenador dadorDeNivel = new NivelEntrenador(niveles);
        ExperienciaValor dadorDeExperiencia = new ExperienciaValor();
        Entrenador pepe = new Entrenador("pipo", dojo, dadorDeExperiencia, dadorDeNivel);
        Especie especie = new Especie("Kalu", TipoBicho.AIRE, 1, 2, 0);
        Bicho kalu = new Bicho(especie);
        Bicho kalu2 = new Bicho(especie);
        guarderia.adoptar(kalu);
        guarderia.adoptar(kalu2);
        pepe.setBichoDollars(300);

        runInSession(()->daou.clear());
        runInSession(()->neodao.borrarTodo()  );
        runInSession(()->mongo.deleteAll()  );
        ms.crearUbicacion(guarderia);
        ms.crearUbicacion(guarderia1);
        ms.crearUbicacion(dojo);
        entservice.guardar(pepe);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,dojo,guarderia);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,dojo,guarderia1);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia1,dojo);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia,dojo);

        ms.mover("pipo","sociales");
        ms.mover("pipo","unqui");
        ms.mover("pipo","chaparral");
        bs.buscar("pipo");                          //"captura bicho"
        bs.buscar("pipo");                         // "captura bicho"
        bs.abandonarBicho("pipo",kalu2.getId());  //  "abandona bicho en guarderia"
        ms.mover("pipo","unqui");      //   "entrenador arriba a"
        bs.duelo("pipo",kalu.getId());          //    "el guachin se corona"
        ms.mover("pipo","chaparral");


        List<Evento> eventos =fs.feedUbicacion("pipo");
        assertEquals(8, eventos.size());
        assertEquals("entrenador arriba a", eventos.get(0).getAccion());
        assertEquals("el guachin se corona", eventos.get(1).getAccion());
    }

}
