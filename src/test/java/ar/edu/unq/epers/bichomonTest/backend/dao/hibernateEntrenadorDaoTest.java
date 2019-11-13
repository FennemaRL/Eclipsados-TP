package ar.edu.unq.epers.bichomonTest.backend.dao;


import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ExperienciaValor;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.NivelEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomontTestBichoService.ProbabilidadNoRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;
import static org.junit.Assert.assertEquals;

public class hibernateEntrenadorDaoTest { //falta testear cosas y agregar constrains pero lo basico de guardar
    private HibernateEntrenadorDao dao ;
    private Entrenador pepe;
    private Ubicacion ubi;

    @Before
    public void crearModelo() {
        ProbabilidadNoRandom pr= new ProbabilidadNoRandom();
        ubi = new Pueblo("1114",pr);
        ArrayList<Integer> num = new ArrayList<>();
        num.add(40);num.add(50);num.add(90);
        dao= new HibernateEntrenadorDao();
        pepe = new Entrenador("pepe", ubi, new ExperienciaValor(1,1,1),new NivelEntrenador());
        Bicho bartolo = new Bicho(new Especie("arnaldo", TipoBicho.AGUA,1,1,0));
        bartolo.setFechaCaptura(new Date());
        pepe.agregarBichomon(bartolo);
    }
    @After
    public void tearDown(){
        runInSession(()-> dao.clear());
    }


    @Test
    public void test_al_guardar_y_luego_recuperar_se_obtiene_objetos_similares(){
        Entrenador pepa = new Entrenador("pepa", ubi, new ExperienciaValor(1,1,1),new NivelEntrenador());
        runInSession(() ->this.dao.guardar(pepa)); // modificar la fecha manaña por que es demasiado larga :S

        Entrenador pep2 = TransactionRunner.runInSession(() ->this.dao.recuperar("pepa"));

        assertEquals(pepa.getUbicacion().getNombreUbicacion(), pep2.getUbicacion().getNombreUbicacion());
        assertEquals(pepa.getNombre(), pep2.getNombre());
    }
    @Test
    public void al_recuperar_un_nombre_inexistente_no_recupera_y_retorna_null(){ // test desfavorable recuperar
        TransactionRunner.runInSession(() ->this.dao.recuperar("pejelagarto3"));

    }

    @Test (expected= PersistenceException.class) //arreglar en clase
    public void al_guardar_dos_objetos_con_el_mismo_nombre_levanta_excepcion_por_constraint_Nombre() { // test contraint nombre
        runInSession(() ->this.dao.guardar(this.pepe));
        Entrenador et = new Entrenador("pepe",ubi);
        runInSession(() ->this.dao.guardar(et));
    }

}
