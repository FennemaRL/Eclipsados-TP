package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB.MongoEventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;
import static org.junit.Assert.assertEquals;

public class MongoTestDao {

    private MongoEventoDAO dao;
    @Before
    public void setup(){
        dao = new MongoEventoDAO();
    }
    @After
    public void teardown(){
        dao.deleteAll();
    }
    @Test
    public void se_guarda_y_se_recupera_un_evento() {

        Ubicacion ub = new Guarderia("panda");
        Entrenador ent = new Entrenador("ent",ub);
        Evento  ev = new Evento(ent);
        runInSession(()->dao.save(ev));
        assertEquals(ev,dao.getByEntrenador("ent").get(0));

    }
}
