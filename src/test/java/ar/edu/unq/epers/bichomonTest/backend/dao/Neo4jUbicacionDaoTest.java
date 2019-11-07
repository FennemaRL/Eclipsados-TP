package ar.edu.unq.epers.bichomonTest.backend.dao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionNeoDao;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.Transporte;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Neo4jUbicacionDaoTest {

    private UbicacionService ubs;
    private UbicacionNeoDao neodao;
    private HibernateUbicacionDao dao;

    @Before
    public void setup(){
         dao = new HibernateUbicacionDao();
         ubs = new UbicacionService(dao);
         neodao = new UbicacionNeoDao(ubs);
        neodao.borrarTodo();
    }
    @After
    public void teard(){
        run(()->dao.clear());
        //neodao.borrarTodo();
    }

    @Test
    public void seguarda_una_ubicacion() {

        Ubicacion guarderia = new Guarderia("1114");
        Ubicacion guarderia2 = new Dojo("1114bis");
        ubs.guardar(guarderia);
        ubs.guardar(guarderia2);
        neodao.crearNodo(guarderia);
        neodao.crearNodo(guarderia2);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia,guarderia2);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia2,guarderia);

        List<Ubicacion> fub = neodao.conectadosp2p(guarderia,Transporte.AEREO);
        assertEquals(1,fub.size());
        assertEquals(guarderia2.getNombre(), fub.get(0).getNombre());
    }
    @Test
    public void ubicaciona_esta_conectada_con_ubicacionb_por_cualquier_medio_directamente() {

        Ubicacion guarderia = new Guarderia("1114");
        Ubicacion guarderia2 = new Dojo("1114bis");
        neodao.crearNodo(guarderia);
        neodao.crearNodo(guarderia2);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia,guarderia2);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia,guarderia2);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia,guarderia2);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia2,guarderia);


        assertTrue(0<neodao.estaConectadop2p(guarderia,guarderia2).size());
        assertTrue(0<neodao.estaConectadop2p(guarderia2,guarderia).size());
    }
    @Test
    public void ubicaciona_esta_conectada_con_ubicacionb_por_cualquier_medio_directamenteono() {

        Ubicacion guarderia = new Guarderia("1114bis1");
        Ubicacion guarderia2 = new Dojo("1114bis2");
        Ubicacion guarderia3 = new Dojo("1114bis3");
        Ubicacion guarderia4 = new Dojo("1114bis4");
        Ubicacion guarderia5 = new Dojo("1114bis5");
        neodao.crearNodo(guarderia);
        neodao.crearNodo(guarderia2);
        neodao.crearNodo(guarderia3);
        neodao.crearNodo(guarderia4);
        neodao.crearNodo(guarderia5);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia,guarderia2);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia,guarderia2);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia,guarderia2);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia2,guarderia5);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia2,guarderia5);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia2,guarderia5);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia,guarderia3);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia,guarderia3);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia,guarderia3);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia3,guarderia4);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia3,guarderia4);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia3,guarderia4);

        neodao.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia4,guarderia5);
        neodao.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia4,guarderia5);
        neodao.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia4,guarderia5);

        assertEquals(0,neodao.estaConectadop2p(guarderia,guarderia5).size());
        assertTrue(0< neodao.estaConectadoN(guarderia,guarderia5).size());

    }
}