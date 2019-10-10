package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.Exception.EspecieNoPuedeEvolucionar;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Energia;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Victoria;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Date;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.*;

public class TestBichoService {
    private Bicho bartolomon;
    private Ubicacion ubicacion;
    private BichoService bs;
    private Energia energia;
    private EntrenadorService es ;
    private Victoria victoria;
    private ExperienciaValor dadorDeExperiencia;
    private NivelEntrenador dadorDeNivel;

    @Before
    public void setUp(){
        SessionFactoryProvider.destroy();
        HibernateEntrenadorDao dao = new HibernateEntrenadorDao();
        dao.clear();
        es = new EntrenadorService(dao);
        bs= new BichoService(es);
        Especie especie = new Especie("Rayo", TipoBicho.ELECTRICIDAD,3,2,0);
        bartolomon = new Bicho(especie);
        ArrayList<Integer>  niveles =  new ArrayList<>();
        niveles.add (2);niveles.add (3);
        dadorDeNivel =new NivelEntrenador(niveles);
        dadorDeExperiencia =new ExperienciaValor();

        //condition
        energia = new Energia();
        victoria= new Victoria();
    }

    @Test(expected = NoHayEntrenadorConEseNombre.class)
    public void al_buscar_un_Entrenador_que_no_existe_levanta_una_excepcion(){// busqueda desfavorable entrenador
        assertEquals(bartolomon, bs.buscar("pepe1"));
    }

    @Test
    //buscar
    public void se_caputura_en_un_guarderia_con_bichomon(){ // busqueda favorable guarderia
        ubicacion = new Guarderia("Chaparral");
        ubicacion.adoptar(bartolomon);
        Entrenador entrenador = new Entrenador("Mostaza",ubicacion,dadorDeExperiencia,dadorDeNivel);
        es.guardar(entrenador) ;

        assertEquals(bartolomon.getId(), bs.buscar("Mostaza").getId());
    }
    @Test(expected = GuarderiaErrorNoBichomon.class)
    public void se_caputura_en_un_guarderia_sin_bichomon(){ // busqueda desfavorable guarderia
        ubicacion = new Guarderia("Chaparral1");
        Entrenador entrenador = new Entrenador("Mostaza1",ubicacion,dadorDeExperiencia,dadorDeNivel);
        es.guardar(entrenador) ;

        bs.buscar("Mostaza1");
    }
    @Test(expected = DojoSinEntrenador.class)
    public void se_caputura_en_un_dojo_sin_campeon(){ // busqueda desfavorable dojo
        ProbabilidadNoRandom mr = new ProbabilidadNoRandom();
        ubicacion = new Dojo("Chaparral2",mr);
        Entrenador entrenador = new Entrenador("Mostaza2",ubicacion,dadorDeExperiencia,dadorDeNivel);
        es.guardar(entrenador) ;

        bs.buscar("Mostaza2");
    }
    @Test
    public void se_captura_un_bicho_Especie_bichorita_en_un_dojo(){ //busqueda favorable dojo
        RandomBichomon mr =new ProbabilidadNoRandom();
        Especie esp =new Especie("bichorita2",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Dojo("Chaparral4",mr);
        Entrenador entrenador = new Entrenador("Mostaza4",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);
        ubicacion.retar(entrenador,bicho);

        es.guardar(entrenador);

        assertEquals(bicho.getEspecie().getNombre(),bs.buscar("Mostaza4").getEspecie().getNombre());
    }
    @Test(expected = CapturaFallida.class)
    public void no_se_captura_un_bicho_en_un_dojo(){ //busqueda favorable dojo
        RandomBichomon mr =new ProbabilidadNoRandomFalse();
        Especie esp =new Especie("bichorita3",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Dojo("Chaparral3",mr);
        Entrenador entrenador = new Entrenador("Mostaza3",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);
        System.out.print(bicho +" bicho?");
        ubicacion.retar(entrenador,bicho);

        es.guardar(entrenador);


        bs.buscar("Mostaza3");
    }
    @Test
    public void se_captura_un_bicho_Especie_bichorita_en_un_pueblo(){ //busqueda favorable pueblo
        RandomBichomon mr =new ProbabilidadNoRandom();
        ArrayList<Especie> esp = new ArrayList<>();
        esp.add(new Especie("bichorita8",TipoBicho.PLANTA, 1,1,0));
        ArrayList<Integer> probesp = new ArrayList<>();
        probesp.add(100);
        ubicacion = new Pueblo("Chaparral8",mr,esp,probesp);
        ArrayList<Integer> niveles = new ArrayList<Integer>();
        niveles.add (2);niveles.add (3);
        Entrenador entrenador = new Entrenador("Mostaza8",ubicacion,dadorDeExperiencia,dadorDeNivel);


        es.guardar(entrenador);

        Bicho bichocap =bs.buscar("Mostaza8");
        assertEquals(esp.get(0).getNombre(),bichocap.getEspecie().getNombre());
        assertEquals(1, bichocap.getEspecie().getCantidadBichos());
    }

    //evolucionar
    @Test
    public void se_le_pregunta_a_un_entrenador_si_su_bichomon_puede_evolucionar(){ //caso bueno evolucionar

        Especie chocoMon =new Especie("chocoMon",TipoBicho.CHOCOLATE, 1,1,0);
        Especie dobleChocoMon = new Especie("dobleChocoMon",TipoBicho.CHOCOLATE, 2,2,0);
        chocoMon.setEspecieEvo(dobleChocoMon);
        chocoMon.setEnergiaIncial(50);
        Bicho ricky = new Bicho(chocoMon);
        Date dt = new Date(2001,10,10);
        ricky.setFechaCaptura(dt);
        ricky.setEnergia(50);
        energia.setPuntos(30);

        RandomBichomon pNR =new ProbabilidadNoRandom();
        ubicacion = new Dojo("fidelHouse",pNR);

        Entrenador lukas = new Entrenador("lukas",ubicacion,dadorDeExperiencia,dadorDeNivel);
        chocoMon.nuevaCondicion(energia);
        lukas.agregarBichomon(ricky);

        es.guardar(lukas);

        assertTrue(bs.puedeEvolucionar("lukas",ricky.getId()));
        assertNotSame(chocoMon,bs.evolucionar("lukas",ricky.getId()));

    }
    @Test(expected = EspecieNoPuedeEvolucionar.class)
    public void se_le_pregunta_a_un_entrenador_si_su_bichomon_puede_evolucionar_y_no_puede(){//caso malo evolucionar

        Especie chocoMon =new Especie("chocoMon1",TipoBicho.CHOCOLATE, 1,1,0);
        Especie dobleChocoMon = new Especie("dobleChocoMon1",TipoBicho.CHOCOLATE, 2,2,0);
        chocoMon.setEspecieEvo(dobleChocoMon);
        chocoMon.setEnergiaIncial(50);
        Bicho ricky = new Bicho(chocoMon);
        Date dt = new Date(2001,10,10);
        ricky.setFechaCaptura(dt);
        ricky.setEnergia(50);
        energia.setPuntos(80);
        victoria.setPuntos(1);

        RandomBichomon pNR =new ProbabilidadNoRandom();
        ubicacion = new Dojo("fidelHouse1",pNR);

        Entrenador lukas = new Entrenador("lukas1",ubicacion);
        chocoMon.nuevaCondicion(energia);
        chocoMon.nuevaCondicion(victoria);
        lukas.agregarBichomon(ricky);

        es.guardar(lukas);

        assertFalse(bs.puedeEvolucionar("lukas1",ricky.getId()));
        bs.evolucionar("lukas1",ricky.getId());

    }

    @Test (expected = EntrenadorException.class)
    public void si_se_le_pregunta_a_un_entrenador_si_un_bichomon_puede_evolucionar_y_no_es_suyo_se_devuelve_una_exception(){

        Especie chocoMon =new Especie("chocoMon2",TipoBicho.CHOCOLATE, 1,1,0);
        Especie dobleChocoMon = new Especie("dobleChocoMon2",TipoBicho.CHOCOLATE, 2,2,0);
        chocoMon.setEspecieEvo(dobleChocoMon);
        chocoMon.setEnergiaIncial(50);
        energia.setPuntos(30);
        victoria.setPuntos(0);

        Bicho ricky = new Bicho(chocoMon);
        Date dt = new Date(2020,10,10);
        ricky.setFechaCaptura(dt);
        chocoMon.nuevaCondicion(energia);
        chocoMon.nuevaCondicion(victoria);

        RandomBichomon pNR =new ProbabilidadNoRandom();
        ubicacion = new Dojo("fidelHouse2",pNR);

        Entrenador lukas = new Entrenador("lukas2",ubicacion,dadorDeExperiencia,dadorDeNivel);
        ricky.setEnergia(50);

        lukas.agregarBichomon(ricky);

        es.guardar(lukas);

        assertFalse(bs.puedeEvolucionar("lukas2",ricky.getId()*2));

    }


    //abandonarBichoTests


    @Test (expected = BichomonError.class)
    public void el_entrenador_puede_abandonar_un_bicho_siempre_que_no_se_quede_sin_bichos(){
        ubicacion = new Guarderia("Chaparral6");
        Entrenador entrenador = new Entrenador("Mostaza6",ubicacion,dadorDeExperiencia,dadorDeNivel);
        Especie chocoMon =new Especie("chocoMon6",TipoBicho.CHOCOLATE, 1,1,0);
        Bicho lisomon = new Bicho(chocoMon);
        Bicho homermon = new Bicho(chocoMon);

        entrenador.agregarBichomon(lisomon);
        entrenador.agregarBichomon(homermon);

        es.guardar(entrenador);

        bs.abandonarBicho(entrenador.getNombre(), lisomon.getId());

        bs.abandonarBicho(entrenador.getNombre(),homermon.getId());


    }

    @Test (expected = ZonaErronea.class)
    public void el_entrenador_no_puede_abandonar_un_bicho_si_no_esta_en_una_guarderia(){
        RandomBichomon mr =new ProbabilidadNoRandom();
        ubicacion = new Dojo("Chaparral7", mr);
        Entrenador entrenador = new Entrenador("Mostaza7",ubicacion,dadorDeExperiencia,dadorDeNivel);
        Especie chocoMon =new Especie("chocoMon7",TipoBicho.CHOCOLATE, 1,1,0);
        Bicho lisomon = new Bicho(chocoMon);
        Bicho homermon = new Bicho(chocoMon);

        entrenador.agregarBichomon(lisomon);
        entrenador.agregarBichomon(homermon);

        es.guardar(entrenador);

        bs.abandonarBicho(entrenador.getNombre(), lisomon.getId());
        //El cambio de owner sobre el bicho esta testeado en el modelo.
        bs.abandonarBicho(entrenador.getNombre(),homermon.getId());
    }

    @Test (expected = EntrenadorException.class)
    public void el_entrenador_no_puede_abandonar_un_bicho_que_no_tiene(){
        ubicacion = new Guarderia("Chaparral8");
        Entrenador entrenador = new Entrenador("Mostaza8",ubicacion,dadorDeExperiencia,dadorDeNivel);
        Especie chocoMon =new Especie("chocoMon8",TipoBicho.CHOCOLATE, 1,1,0);
        Bicho lisomon = new Bicho(chocoMon);
        Bicho homermon = new Bicho(chocoMon);

        entrenador.agregarBichomon(lisomon);
        entrenador.agregarBichomon(homermon);

        es.guardar(entrenador);

        bs.abandonarBicho(entrenador.getNombre(), 99);

    }
    //duelo
    @Test
    public void retar_en_un_dojo_sin_campeon(){ // sin campeon
        RandomBichomon mr =new ProbabilidadNoRandom();
        Especie esp =new Especie("bichorita11",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Dojo("Chaparral11",mr);
        Entrenador entrenador = new Entrenador("Mostaza11",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);

        es.guardar(entrenador);

        assertEquals(bicho.getId(),bs.duelo("Mostaza11",bicho.getId()).getGanador().getId());

    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_retar_en_un_pueblo(){
        RandomBichomon mr =new ProbabilidadNoRandom();
        Especie esp =new Especie("bichorita11",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Pueblo("Chaparral11",mr);
        Entrenador entrenador = new Entrenador("Mostaza11",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);

        es.guardar(entrenador);

        bs.duelo("Mostaza11",bicho.getId());

    }
    @Test(expected = ZonaErronea.class)
    public void se_trata_de_retar_en_una_guarderia(){
        Especie esp =new Especie("bichorita11",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Guarderia("Chaparral11");
        Entrenador entrenador = new Entrenador("Mostaza11",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);

        es.guardar(entrenador);

        bs.duelo("Mostaza11",bicho.getId());

    }
    @Test(expected =BichomonError.class)
    public void el_campeon_de_un_dojo_no_se_puede_desafiar_a_si_mismo(){ // mismo campeon
        RandomBichomon mr =new ProbabilidadNoRandom();
        Especie esp =new Especie("bichorita12",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Dojo("Chaparral12",mr);
        Entrenador entrenador = new Entrenador("Mostaza12",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);

        es.guardar(entrenador);

        bs.duelo("Mostaza12",bicho.getId());
        bs.duelo("Mostaza12",bicho.getId());
    }
    @Test
    public void el_campeon_de_un_dojo_es_distituido(){ // desplaza3 //completar
        RandomBichomon mr =new ProbabilidadNoRandom();
        Especie esp =new Especie("bichorita12",TipoBicho.PLANTA, 1,1,0);
        Bicho bicho =new Bicho(esp);

        ubicacion = new Dojo("Chaparral12",mr);
        Entrenador entrenador = new Entrenador("Mostaza12",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);
        es.guardar(entrenador);
        Bicho bicho2 =new Bicho(esp);
        bicho2.setEnergia(500);
        Entrenador entrenador2 = new Entrenador("Mostaza13",ubicacion,dadorDeExperiencia,dadorDeNivel);
        entrenador.agregarBichomon(bicho);
        entrenador2.agregarBichomon(bicho2);
        es.guardar(entrenador2);
        ResultadoCombate rc1 =bs.duelo("Mostaza12",bicho.getId());

        ResultadoCombate rc =bs.duelo("Mostaza13",bicho2.getId());

        assertEquals(bicho.getId(), rc1.getGanador().getId());
        assertEquals(bicho2.getId(), rc.getGanador().getId());
        assertNotSame(rc1.getGanador().getId(), rc.getGanador().getId());
    }

}