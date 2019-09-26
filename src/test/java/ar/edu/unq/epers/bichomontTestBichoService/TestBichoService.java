package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;

public class TestBichoService { // los test no corren en conjunto ya que las tablas siguen persistiendo
    private Bicho bicho ;
    private Ubicacion ubicacion;
    private HibernateEntrenadorDao edao;
    private BichoService bs;

    @Before
    public void setUp(){

        edao=new HibernateEntrenadorDao();
        bs= new BichoService(edao,new HibernateEspecieDao(),new HibernateBichoDao());
        Especie especie = new Especie("Rayo", TipoBicho.ELECTRICIDAD,3,2,0);
        bicho = new Bicho(especie);


    }
    @After
    public void tearDown(){
    }

    @Test(expected = NoHayEntrenadorConEseNombre.class)
    public void al_buscar_un_Entrenador_que_no_existe_levanta_una_excepcion(){// busqueda desfavorable entrenador
        assertEquals(bicho, bs.buscar("pepe1"));
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
        Entrenador entrenador = new Entrenador("Mostaza1",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

        bs.buscar("Mostaza1");
    }
    @Test(expected = DojoSinEntrenador.class)
    public void se_caputura_en_un_dojo_sin_campeon(){ // busqueda desfavorable dojo
        ProbabilidadNoRandom mr = new ProbabilidadNoRandom();
        ubicacion = new Dojo("Chaparral",mr);
        Entrenador entrenador = new Entrenador("Mostaza2",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

       bs.buscar("Mostaza2");
    }
    @Test
    public void se_captura_un_bicho_Especie_bichorita_en_un_pueblo(){ //busqueda favorable pueble
        RandomBichomon mr =new ProbabilidadNoRandom();
        ArrayList<Especie> esp = new ArrayList<>();
        esp.add(new Especie("bichorita",TipoBicho.PLANTA, 1,1,0));
        ArrayList<Integer> probesp = new ArrayList<>();
        probesp.add(100);
        ubicacion = new Pueblo("Chaparral",mr,esp,probesp);
        Entrenador entrenador = new Entrenador("Mostaza3",ubicacion);


        run(() ->edao.guardar(entrenador));

        Bicho bichocap =bs.buscar("Mostaza3");
        assertEquals(esp.get(0).getNombre(),bichocap.getEspecie().getNombre());
        assertEquals(1, bichocap.getEspecie().getCantidadBichos());
    }
    @Test
    public void se_captura_un_bicho_Especie_bichorita_en_un_dojo(){ //busqueda favorable pueble
        RandomBichomon mr =new ProbabilidadNoRandom();
        Especie esp =new Especie("bichorita2",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Dojo("Chaparral",mr);
        Entrenador entrenador = new Entrenador("Mostaza4",ubicacion);
        entrenador.agregarBichomon(bicho);
        ubicacion.retar(entrenador,bicho);

        run(() ->edao.guardar(entrenador));

        assertEquals(bicho.getEspecie().getNombre(),bs.buscar("Mostaza4").getEspecie().getNombre());
    }
}
