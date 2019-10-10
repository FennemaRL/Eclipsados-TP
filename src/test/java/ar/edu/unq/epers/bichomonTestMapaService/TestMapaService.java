package ar.edu.unq.epers.bichomonTestMapaService;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDao;
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
import java.security.Guard;

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
        SessionFactoryProvider.destroy();
    }

    @Test
    public void al_mover_al_entrenador_de_guarderia1_a_guarderia2_su_ubicacion_actual_pasa_a_ser_guarderia2(){
        mapaService.mover("esh","guarderia2");
        Entrenador entrenador2 = entrenadorService.recuperar("esh");
        assertEquals("guarderia2", entrenador2.getUbicacion().getNombreUbicacion());
    }

    @Test
    public void una_ubicacion_sabe_cuantos_entrenadores_hay_en_la_misma(){

        assertEquals(mapaService.cantidadEntrenadores("guarderia1"),0);
        assertEquals(mapaService.cantidadEntrenadores("guarderia2"),5);
    }


}
