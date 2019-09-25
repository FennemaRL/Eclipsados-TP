package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBichoService {
    private Bicho bicho ;
    private BichoService bs;
    private Ubicacion ubicacion;
    private HibernateEntrenadorDao edao;

    @Before
    public void setUp(){
        edao = new HibernateEntrenadorDao();
        bs = new BichoService(edao,new HibernateEspecieDao(),new HibernateBichoDao());
        Especie especie = new Especie("Rayo", TipoBicho.ELECTRICIDAD);
        bicho = new Bicho(especie);


    }
    @Test(expected = NoHayEntrenadorConEseNombre.class)
    public void al_buscar_un_Entrenador_que_no_existe_levanta_una_excepcion(){
        assertEquals(bicho, bs.buscar("pepe"));
    }
    @Test
    public void se_caputura_en_un_guarderia_con_bichomon(){
        ubicacion = new Guarderia("Chaparral");
        ubicacion.adoptar(bicho);
        Entrenador entrenador = new Entrenador("Mostaza",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

        assertEquals(bicho.getId(), bs.buscar("Mostaza").getId());
    }
}
