package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.*;

public class TestBichoService { // los test no corren en conjunto ya que las tablas siguen persistiendo
    private Bicho bartolomon;
    private Ubicacion ubicacion;
    private HibernateEntrenadorDao edao;
    private BichoService bs;

    @Before
    public void setUp(){

        edao=new HibernateEntrenadorDao();
        bs= new BichoService(edao,new HibernateEspecieDao(),new HibernateBichoDao());
        Especie especie = new Especie("Rayo", TipoBicho.ELECTRICIDAD,3,2,0);
        bartolomon = new Bicho(especie);



    }
    @After
    public void tearDown(){
    }

    @Test(expected = NoHayEntrenadorConEseNombre.class)
    public void al_buscar_un_Entrenador_que_no_existe_levanta_una_excepcion(){// busqueda desfavorable entrenador
        assertEquals(bartolomon, bs.buscar("pepe1"));
    }

    @Test
    public void se_caputura_en_un_guarderia_con_bichomon(){ // busqueda favorable guarderia
        ubicacion = new Guarderia("Chaparral");
        ubicacion.adoptar(bartolomon);
        Entrenador entrenador = new Entrenador("Mostaza",ubicacion);
        run(() ->edao.guardar(entrenador)) ;

        assertEquals(bartolomon.getId(), bs.buscar("Mostaza").getId());
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

    @Test
    public void se_le_pregunta_a_un_entrenador_ssi_su_bichomon_puede_evolucionar(){

        Especie chocoMon =new Especie("chocoMon",TipoBicho.CHOCOLATE, 1,1,0);
        Especie dobleChocoMon = new Especie("dobleChocoMon",TipoBicho.CHOCOLATE, 2,2,0);
        chocoMon.setEspecieEvo(dobleChocoMon);
        chocoMon.setEnergiaIncial(50);
        Bicho ricky = new Bicho(chocoMon);
        Date dt = new Date(2001,10,10);
        ricky.setFechaCaptura(dt);
        ricky.setEnergia(50);

        RandomBichomon pNR =new ProbabilidadNoRandom();
        ubicacion = new Dojo("fidelHouse",pNR);

        Entrenador lukas = new Entrenador("lukas",ubicacion);
        chocoMon.setCondicionesEvolucion(30,0,lukas.getNivel(),0);
        lukas.agregarBichomon(ricky);

        run(() ->edao.guardar(lukas));

        assertTrue(bs.puedeEvolucionar("lukas",ricky.getId()));

    }
    @Test
    public void se_le_pregunta_a_un_entrenador_si_su_bichomon_puede_evolucionar(){

        Especie chocoMon =new Especie("chocoMon",TipoBicho.CHOCOLATE, 1,1,0);
        Especie dobleChocoMon = new Especie("dobleChocoMon",TipoBicho.CHOCOLATE, 2,2,0);
        chocoMon.setEspecieEvo(dobleChocoMon);
        chocoMon.setEnergiaIncial(50);

        Bicho ricky = new Bicho(chocoMon);
        Date dt = new Date(2020,10,10);
        ricky.setFechaCaptura(dt);

        RandomBichomon pNR =new ProbabilidadNoRandom();
        ubicacion = new Dojo("fidelHouse",pNR);

        Entrenador lukas = new Entrenador("lukas",ubicacion);
        chocoMon.setCondicionesEvolucion(90,10,10,20);
        lukas.agregarBichomon(ricky);

        run(() ->edao.guardar(lukas));

        assertFalse(bs.puedeEvolucionar("lukas",ricky.getId()));

    }

    @Test (expected = EntrenadorException.class)
    public void si_se_le_pregunta_a_un_entrenador_si_un_bichomon_puede_evolucionar_y_no_es_suyo_se_devuelve_una_exception(){

        Especie chocoMon =new Especie("chocoMon",TipoBicho.CHOCOLATE, 1,1,0);
        Especie dobleChocoMon = new Especie("dobleChocoMon",TipoBicho.CHOCOLATE, 2,2,0);
        chocoMon.setEspecieEvo(dobleChocoMon);
        chocoMon.setEnergiaIncial(50);

        Bicho ricky = new Bicho(chocoMon);
        Date dt = new Date(2020,10,10);
        ricky.setFechaCaptura(dt);

        RandomBichomon pNR =new ProbabilidadNoRandom();
        ubicacion = new Dojo("fidelHouse",pNR);

        Entrenador lukas = new Entrenador("lukas",ubicacion);
        chocoMon.setCondicionesEvolucion(90,10,10,20);
        lukas.agregarBichomon(ricky);

        run(() ->edao.guardar(lukas));

        assertFalse(bs.puedeEvolucionar("lukas",ricky.getId()*2));

    }


    //abandonarBichoTest
    @Test (expected = BichomonError.class)
    public void el_entrenador_puede_abandonar_un_bicho_siempre_que_no_se_quede_sin_bichos(){
        RandomBichomon mr =new ProbabilidadNoRandom();
        ubicacion = new Dojo("Chaparral", mr );
        Entrenador entrenador = new Entrenador("Mostaza5",ubicacion);
        Especie chocoMon =new Especie("chocoMon",TipoBicho.CHOCOLATE, 1,1,0);
        Bicho lisomon = new Bicho(chocoMon);
        Bicho homermon = new Bicho(chocoMon);

        entrenador.agregarBichomon(lisomon);
        entrenador.agregarBichomon(homermon);

        run(() ->edao.guardar(entrenador));

        bs.abandonarBicho(entrenador.getNombre(), lisomon.getId());

        bs.abandonarBicho(entrenador.getNombre(),homermon.getId());

        
    }

}
