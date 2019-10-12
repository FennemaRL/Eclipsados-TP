package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayEntrenadorConEseNombre;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;

public class TestEntrenadorService {

    private EntrenadorService es;
    private Entrenador entrenador;
    private HibernateEntrenadorDao dao;

    @Before
    public void setup(){
        dao = new HibernateEntrenadorDao();
        es =new EntrenadorService(dao);
        Ubicacion guarde = new Guarderia("1116");
        entrenador = new Entrenador("pepe",guarde);

        es.guardar(entrenador);
    }
    @After
    public void tearDown(){
        run(()-> dao.clear());
    }
    @Test(expected = NoHayEntrenadorConEseNombre.class)
    public void al_recuperar_un_entrenador_inexistente_levanta_una_exepcion(){
       es.recuperar("pepe1");
    }

    @Test
    public void al_guardar_y_recuperar_un_entrenador_este_tiene_los_mismos_datos(){


        Entrenador mismoEntrenador= es.recuperar(entrenador.getNombre());

        assertEquals(entrenador.getNombre(),mismoEntrenador.getNombre());
        assertEquals(entrenador.getUbicacion().getNombreUbicacion(), mismoEntrenador.getUbicacion().getNombreUbicacion());
    }
    @Test
    public void al_acutalizar_y_recuperar_un_entrenador_este_tiene_los_mismos_datos(){
        Ubicacion ubicacion = new Guarderia("11231");
        entrenador.setUbicacion(ubicacion);
        es.actualizar(entrenador);
        Entrenador mismoEntrenador = es.recuperar(entrenador.getNombre());
        assertEquals(entrenador.getNombre(),mismoEntrenador.getNombre());
        assertEquals(entrenador.getUbicacion().getNombreUbicacion(), mismoEntrenador.getUbicacion().getNombreUbicacion());


    }
}
