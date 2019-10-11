package ar.edu.unq.epers.bichomonTestMapaService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.security.Guard;

public class  TestMapaService {

    private Entrenador esh;
    private Ubicacion guarderia2;
    private Ubicacion guarderia1;

    private HibernateEntrenadorDao dao;
    private EntrenadorService entrenadorService;
    private UbicacionService ubicacionService;
    private MapaService mapaService;




    @Before
    public void setUp(){
        guarderia1 = new Guarderia("guarderia1");
        guarderia2 = new Guarderia("guarderia2");

        esh = new Entrenador("esh", guarderia1);

        dao = new HibernateEntrenadorDao();
        entrenadorService = new EntrenadorService(dao);
        ubicacionService = new UbicacionService(new HibernateUbicacionDao());
        mapaService = new MapaService(entrenadorService, ubicacionService);

        entrenadorService.guardar(esh);
        ubicacionService.guardar(guarderia2);


        }
    @After
    public void tearDown(){
        SessionFactoryProvider.destroy();
    }

    @Test
    public void al_mover_al_entrenador_de_guarderia1_a_guarderia2_su_ubicacion_actual_pasa_a_ser_guarderia2(){
        mapaService.mover("esh","guarderia2");
        Entrenador entrenador2 = entrenadorService.recuperar("esh");
        assertEquals("guarderia2", entrenador2.getUbicacion().getNombreUbicacion());
    }

    @Test
    public void al_mover_al_entrenador_la_cantidad_de_entrenadores_en_cada_ubicacion_cambia(){
        assertEquals(1,guarderia1.getCantidadDeEntrenadores());
        assertEquals(0,guarderia2.getCantidadDeEntrenadores());

        mapaService.mover("esh","guarderia2");
        Ubicacion guarderia11 = ubicacionService.recuperar("guarderia1");
        Ubicacion guarderia22 = ubicacionService.recuperar("guarderia2");

        assertEquals(0, guarderia11.getCantidadDeEntrenadores());
        assertEquals(1,guarderia22.getCantidadDeEntrenadores());
    }

}
