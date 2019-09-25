package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
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
    public void al_buscar_un_Entrenador_que_no_existe_levanta_una_excepcion(){// busqueda desfavorable entrenador
        assertEquals(bicho, bs.buscar("pepe"));
    }
    @Test
    public void se_caputura_en_un_guarderia_con_bichomon(){ // busqueda favorable guarderia
        ubicacion = new Guarderia("Chaparral");
        ubicacion.adoptar(bicho);
        Entrenador entrenador = new Entrenador("Mostaza",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

        assertEquals(bicho.getId(), bs.buscar("Mostaza").getId());
    }
    @Test(expected = GuarderiaErrorNoBichomon.class)
    public void se_caputura_en_un_guarderia_sin_bichomon(){ // busqueda desfavorable guarderia
        ubicacion = new Guarderia("Chaparral");
        Entrenador entrenador = new Entrenador("Mostaza",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

        bs.buscar("Mostaza");
    }
    @Test(expected = DojoSinEntrenador.class)
    public void se_caputura_en_un_dojo_sin_campeon(){ // busqueda desfavorable dojo
        ubicacion = new Dojo("Chaparral");
        Entrenador entrenador = new Entrenador("Mostaza",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

       bs.buscar("Mostaza").getId();
    }
}
