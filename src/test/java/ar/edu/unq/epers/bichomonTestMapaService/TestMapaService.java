package ar.edu.unq.epers.bichomonTestMapaService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayEntrenadorConEseNombre;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayUbicacionConEseNombre;

import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionNeoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.*;
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;
import static org.junit.Assert.*;


import java.util.ArrayList;

public class  TestMapaService {

    private Entrenador esh;
    private Ubicacion guarderia3;
    private Ubicacion guarderia2;
    private Ubicacion guarderia1;

    private HibernateEntrenadorDao dao;
    private HibernateUbicacionDao ubiDao;
    private EntrenadorService entrenadorService;
    private UbicacionService ubiService;
    private MapaService mapaService;
    private UbicacionNeoDao neo;


    @Before
    public void setUp(){
        guarderia1 = new Guarderia("guarderia1");
        guarderia2 = new Guarderia("guarderia2");

        esh = new Entrenador("esh", guarderia1);

        dao = new HibernateEntrenadorDao();

        ubiDao = new HibernateUbicacionDao();
        entrenadorService = new EntrenadorService(dao);

        ubiService = new UbicacionService(ubiDao);
        guarderia2.setCantidadDeEntrenadores(5);
        ubiService.guardar(guarderia1);
        neo = new UbicacionNeoDao(ubiService);
        mapaService = new MapaService(entrenadorService, ubiService,neo);

        entrenadorService.guardar(esh);
        ubiService.guardar(guarderia2);



        }
    @After
    public void tearDown(){
        runInSession(()-> dao.clear());
        neo.borrarTodo();
    }

    @Test(expected = UbicacionMuyLejana.class)
    public void al_mover_al_entrenador_de_guarderia1_a_guarderia2_falla_por_que_no_estan_relacionadas_directamente(){//ubicacionMuyLejana
        neo.crearNodo(guarderia2);
        neo.crearNodo(guarderia1);
        mapaService.mover("esh","guarderia2");
    }
    @Test(expected = BichomonError.class)
    public void al_mover_al_entrenador_de_guarderia1_a_guarderia2_falla_por_que_esta_pobre_el_entrenador(){//no bichodollars
        neo.crearNodo(guarderia2);
        neo.crearNodo(guarderia1);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia1,guarderia2);
        mapaService.mover("esh","guarderia2");
    }
    @Test
    public void al_mover_al_entrenador_de_guarderia1_a_guarderia2_su_ubicacion_actual_pasa_a_ser_guarderia2(){//caso bueno :)
        neo.crearNodo(guarderia2);
        neo.crearNodo(guarderia1);
        neo.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia1,guarderia2);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia1,guarderia2);
        neo.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia1,guarderia2);
        esh.setBichoDollars(30);
        entrenadorService.actualizar(esh);
        mapaService.mover("esh","guarderia2");
        Entrenador e = entrenadorService.recuperar("esh");
        assertEquals(25,e.cantBichoDollars());
        assertEquals(guarderia2.getNombre(),e.getUbicacion().getNombre());
    }

    @Test
    public void al_mover_al_entrenador_la_cantidad_de_entrenadores_en_cada_ubicacion_cambia(){
        assertEquals(1,guarderia1.getCantidadDeEntrenadores());
        assertEquals(5,guarderia2.getCantidadDeEntrenadores());

        neo.crearNodo(guarderia2);
        neo.crearNodo(guarderia1);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia1,guarderia2);
        esh.setBichoDollars(30);
        entrenadorService.actualizar(esh);
        mapaService.mover("esh","guarderia2");

        Ubicacion guarderia11 = ubiService.recuperar("guarderia1");
        Ubicacion guarderia22 = ubiService.recuperar("guarderia2");

        assertEquals(0, guarderia11.getCantidadDeEntrenadores());
        assertEquals(6,guarderia22.getCantidadDeEntrenadores());
    }

    @Test (expected = NoHayEntrenadorConEseNombre.class)
    public void al_mover_un_entrenador_que_no_existe_salta_NoHayEntrenadorConEseNombre_Exception(){
        mapaService.mover("ash", "guarderia2");
    }

    @Test (expected = NoHayUbicacionConEseNombre.class)
    public void al_mover_a_una_ubicacion_que_no_existe_salta_NoHayUbicacionConEseNombre_Exception(){
        mapaService.mover("esh", "guarderia8");
    }

   @Test
    public void dojo_con_campeon_historico() throws InterruptedException { //
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Dojo dojo = new Dojo("unqui", pr);
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(2);
        niveles.add(3);
        NivelEntrenador dadorDeNivel = new NivelEntrenador(niveles);
        ExperienciaValor dadorDeExperiencia = new ExperienciaValor();
        Entrenador pepe = new Entrenador("pepe", dojo, dadorDeExperiencia, dadorDeNivel);
        Especie especie = new Especie("Kalu", TipoBicho.AIRE, 1, 2, 0);
        Bicho kalu = new Bicho(especie);
        Entrenador pepe2 = new Entrenador("pepe2", dojo, dadorDeExperiencia, dadorDeNivel);
        Bicho kalu2 = new Bicho(especie);
        kalu2.setEnergia(500);
        pepe2.agregarBichomon(kalu2);
        pepe.agregarBichomon(kalu);
        pepe.duelear();
        Thread.sleep(20000);
        pepe2.duelear();
        entrenadorService.guardar(pepe2);

        assertEquals(kalu.getId(), mapaService.campeonHistorico("unqui").getId() );
    }
    @Test
    public void dojo_sin_campeon_historico()  { //funca
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Dojo dojo = new Dojo("unqui", pr);
        ubiService.guardar(dojo);

        assertNull(mapaService.campeonHistorico("unqui"));

    }
    @Test
    public void guarderia_campeon_historico()  { //funca
        ProbabilidadNoRandom pr = new ProbabilidadNoRandom();
        Guarderia dojo = new Guarderia("unqui");
        ubiService.guardar(dojo);

        assertNull(mapaService.campeonHistorico("unqui"));

    }
    @Test
    public void una_ubicacion_sabe_cuantos_entrenadores_hay_en_la_misma(){

        assertEquals(1,mapaService.cantidadEntrenadores("guarderia1"));
        assertEquals(5,mapaService.cantidadEntrenadores("guarderia2"));
    }

    @Test
    public void campeon_retorna_el_campeon_del_dojo(){
        RandomBichomon rb = new RandomBichomon();
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(2);
        niveles.add(3);
        NivelEntrenador dadorDeNivel = new NivelEntrenador(niveles);
        ExperienciaValor expGen = new ExperienciaValor();
        Ubicacion dojojo = new Dojo ("Varela",rb);
        Especie esp = new Especie("papa",CHOCOLATE,0,0,0);
        Bicho bicho = new Bicho(esp);
        Entrenador ent = new Entrenador("sada",dojojo,expGen,dadorDeNivel);

        ent.agregarBichomon(bicho);

        ent.duelear();
        ubiService.guardar(dojojo);

        assertEquals(bicho.getId(),mapaService.campeon("Varela").getId());

    }
    @Test(expected = DojoSinEntrenador.class)
    public void no_retorna_el_campeon_del_dojo(){
        RandomBichomon rb = new RandomBichomon();
        Ubicacion dojojo = new Dojo ("Varela",rb);
        ubiService.guardar(dojojo);

        mapaService.campeon("Varela");

    }
    @Test
    public void mover_mas_corto(){ // caso bueno
        Guarderia guarderia3 = new Guarderia("toti");
        Guarderia guarderia4 = new Guarderia("toti2");
        neo.crearNodo(guarderia2);
        neo.crearNodo(guarderia1);
        neo.crearNodo(guarderia3);
        neo.crearNodo(guarderia4);
        ubiService.guardar(guarderia3);
        ubiService.guardar(guarderia4);
        neo.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia1,guarderia3);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia1,guarderia3);
        neo.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia3,guarderia2);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia3,guarderia2);
        neo.crearRelacionDeUbiAUbi(Transporte.MARITIMO,guarderia3,guarderia4);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia4,guarderia2);

        esh.setBichoDollars(30);
        entrenadorService.actualizar(esh);
        mapaService.moverMasCorto("esh","guarderia2");
        Entrenador e = entrenadorService.recuperar("esh");
        assertEquals(guarderia2.getNombre(),e.getUbicacion().getNombre());
        assertEquals(20,e.cantBichoDollars());
    }
    @Test(expected = UbicacionMuyLejana.class)
    public void mover_mas_corto_sin_conexion(){ // caso malo testear
        Guarderia guarderia3 = new Guarderia("toti");
        neo.crearNodo(guarderia2);
        neo.crearNodo(guarderia1);
        neo.crearNodo(guarderia3);
        ubiService.guardar(guarderia3);
        neo.crearRelacionDeUbiAUbi(Transporte.AEREO,guarderia1,guarderia3);
        neo.crearRelacionDeUbiAUbi(Transporte.TERRESTRE,guarderia1,guarderia3);

        mapaService.moverMasCorto("esh","guarderia2");
    }

    @Test
    public void testCrearUbicacion(){
        guarderia3 = new Guarderia("guarderia3");
        guarderia3.setCantidadDeEntrenadores(234875);
        mapaService.crearUbicacion(guarderia3);
        assertEquals((mapaService.recuperarUbicacion("guarderia3")).getNombre(), guarderia3.getNombre());
    }

    @Test (expected = ConstraintViolationException.class)
    public void noSePuedeCrearUnaUbicacionSiEstaYaExiste(){
        guarderia3 = new Guarderia("guarderia3");
        guarderia3.setCantidadDeEntrenadores(234875);
        mapaService.crearUbicacion(guarderia3);
        mapaService.crearUbicacion(guarderia3);
        assertFalse(neo.existeUbicacion(guarderia1));
    }


}
