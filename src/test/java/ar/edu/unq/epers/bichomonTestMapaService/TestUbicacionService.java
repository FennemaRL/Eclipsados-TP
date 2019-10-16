package ar.edu.unq.epers.bichomonTestMapaService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;

public class TestUbicacionService {

    private UbicacionService ubService;
    private Ubicacion guarderia;
    private HibernateUbicacionDao dao;

    @Before
    public void setUp(){
        dao = new HibernateUbicacionDao();
        ubService = new UbicacionService(dao);
        guarderia = new Guarderia("guarderia");

        ubService.guardar(guarderia);
    }

    @After
    public void tearDown(){
        run(()-> dao.clear());
    }


    @Test
    public void al_guardar_y_recuperar_una_guarderia_esta_tiene_el_mismo_nombre(){
        System.out.print(guarderia.getid());

        Ubicacion mismaUbicacion= ubService.recuperar(guarderia.getNombre());

        assertEquals(guarderia.getNombre(),mismaUbicacion.getNombre());
    }

}
