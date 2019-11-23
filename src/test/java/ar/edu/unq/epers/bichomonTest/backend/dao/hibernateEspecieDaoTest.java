package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.ErrorRecuperar;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.persistence.PersistenceException;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class hibernateEspecieDaoTest{
   private HibernateEspecieDao dao = new HibernateEspecieDao();
    private Especie pejelagarto;

    @Before
    public void crearModelo() {
        this.pejelagarto = new Especie("pejelagarto0",  TipoBicho.CHOCOLATE,1,1,0);
        this.pejelagarto.setPeso(15);
        this.pejelagarto.setAltura(198);
        this.pejelagarto.setCantidadBichos(0);
    }
    @After
    public void tearDown(){
        runInSession(()-> dao.clear());
    }

    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() { //test favorable guardar/recuperar
        Especie pejelagarto1 = new Especie("pejelagarto1",  TipoBicho.CHOCOLATE,1,1,0);
        runInSession(() ->this.dao.guardar(pejelagarto1));

        Especie otraMismaEspecie=  TransactionRunner.runInSession(() ->this.dao.recuperar("pejelagarto1"));


        assertEquals(pejelagarto1.getNombre(), otraMismaEspecie.getNombre());
        assertEquals(pejelagarto1.getPeso(), otraMismaEspecie.getPeso());
        assertEquals(pejelagarto1.getAltura(), otraMismaEspecie.getAltura());
        assertEquals(pejelagarto1.getCantidadBichos(), otraMismaEspecie.getCantidadBichos());
        assertTrue(pejelagarto1 != otraMismaEspecie);
        assertEquals(pejelagarto1.getId(), otraMismaEspecie.getId()) ;
    }
    @Test
    public void al_actualizar_los_objetos_son_similares() { // test favorable actualizar
        runInSession(() ->this.dao.guardar(this.pejelagarto));
        Especie otraMismaEspecie= TransactionRunner.runInSession(() ->this.dao.recuperar((int)pejelagarto.getId()));

        otraMismaEspecie.setPeso(20);
        otraMismaEspecie.setAltura(400);

        runInSession(()->this.dao.actualizar(otraMismaEspecie));
        Especie otraMismaEspecie1= TransactionRunner.runInSession(()->this.dao.recuperar("pejelagarto0"));
        assertEquals(otraMismaEspecie.getNombre(), otraMismaEspecie1.getNombre());
        assertEquals(otraMismaEspecie.getPeso(), otraMismaEspecie1.getPeso());
        assertEquals(otraMismaEspecie.getAltura(), otraMismaEspecie1.getAltura());
        assertEquals(otraMismaEspecie.getCantidadBichos(), otraMismaEspecie1.getCantidadBichos());
    }

    @Test(expected = ErrorRecuperar.class)
    public void al_recuperar_un_nombre_inexistente_no_recupera_y_levanta_excepcion(){ // test desfavorable recuperar
        TransactionRunner.runInSession(() ->this.dao.recuperar("pejelagarto3"));
    }

    @Test (expected= PersistenceException.class) //arreglar en clase
    public void al_guardar_dos_objetos_con_el_mismo_nombre_levanta_excepcion_por_constraint_Nombre() { // test contraint nombre
        runInSession(() ->this.dao.guardar(this.pejelagarto));
        Especie pejelagarto2 = new Especie("pejelagarto0",CHOCOLATE,1,1,0);
        pejelagarto2.setPeso(151);
        pejelagarto2.setAltura(1980);
        pejelagarto2.setCantidadBichos(1);
        runInSession(() ->this.dao.guardar(pejelagarto2));
    }
}
