package ar.edu.unq.epers.bichomonTest.backend.dao;
import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionNeoDao;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;


public class Neo4jUbicacionDaoTest {

    private UbicacionService ubs;
    private UbicacionNeoDao neodao;
    private HibernateUbicacionDao dao;

    @Before
    public void setup(){
         dao = new HibernateUbicacionDao();
         ubs = new UbicacionService(dao);
         neodao = new UbicacionNeoDao(ubs);
    }
    @After
    public void teard(){
        run(()->dao.clear());
        neodao.borrartodo();

    }

    @Test
    public void seguarda_una_ubicacion() {

        Ubicacion guarderia = new Guarderia("1114");
        Ubicacion guarderia2 = new Dojo("1114bis");
        ubs.guardar(guarderia);
        ubs.guardar(guarderia2);
        neodao.crearNodo(guarderia);
        neodao.crearNodo(guarderia2);

        neodao.crearRelacionPorMedioDe("aire",guarderia,guarderia2);

        List<Ubicacion> fub = neodao.seViajaDesde(guarderia);
        assertEquals(guarderia2.getNombre(), fub.get(0).getNombre());
    }
}