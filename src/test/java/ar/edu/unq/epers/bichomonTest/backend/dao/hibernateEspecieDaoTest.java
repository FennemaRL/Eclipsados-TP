package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.ErrorRecuperar;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Before;
import org.junit.Test;


import javax.persistence.PersistenceException;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class hibernateEspecieDaoTest{
   private HibernateEspecieDao dao = new HibernateEspecieDao();
    private Especie pejelagarto;

    @Before
    public void crearModelo() {
        this.pejelagarto = new Especie("pejelagarto",  TipoBicho.CHOCOLATE,1,1,0);
        this.pejelagarto.setPeso(15);
        this.pejelagarto.setAltura(198);
        this.pejelagarto.setCantidadBichos(0);
    }


    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() { //test favorable guardar/recuperar
        run(() ->this.dao.guardar(this.pejelagarto));

        Especie otraMismaEspecie=  run(() ->this.dao.recuperar(pejelagarto.getId()));

        assertEquals(this.pejelagarto.getNombre(), otraMismaEspecie.getNombre());
        assertEquals(this.pejelagarto.getPeso(), otraMismaEspecie.getPeso());
        assertEquals(this.pejelagarto.getAltura(), otraMismaEspecie.getAltura());
        assertEquals(this.pejelagarto.getCantidadBichos(), otraMismaEspecie.getCantidadBichos());
        assertTrue(this.pejelagarto != otraMismaEspecie);
        assertEquals(pejelagarto.getId(), otraMismaEspecie.getId()) ;
    }
    @Test
    public void al_actualizar_los_objetos_son_similares() { // test favorable actualizar
        run(() ->this.dao.guardar(this.pejelagarto));
        Especie otraMismaEspecie= run(() ->this.dao.recuperar(pejelagarto.getId()));

        otraMismaEspecie.setPeso(20);
        otraMismaEspecie.setAltura(400);

        run(()->this.dao.guardar(otraMismaEspecie));
        Especie otraMismaEspecie1= run(()->this.dao.recuperar(otraMismaEspecie.getId()));

        assertEquals(otraMismaEspecie.getNombre(), otraMismaEspecie1.getNombre());
        assertEquals(otraMismaEspecie.getPeso(), otraMismaEspecie1.getPeso());
        assertEquals(otraMismaEspecie.getAltura(), otraMismaEspecie1.getAltura());
        assertEquals(otraMismaEspecie.getCantidadBichos(), otraMismaEspecie1.getCantidadBichos());
    }

    @Test(expected = ErrorRecuperar.class)
    public void al_recuperar_un_nombre_inexistente_no_recupera_y_levanta_excepcion(){ // test desfavorable recuperar
        run(() ->this.dao.recuperar("pejelagarto3"));
    }

    @Test (expected= PersistenceException.class) //arreglar en clase
    public void al_guardar_dos_objetos_con_el_mismo_nombre_levanta_excepcion_por_constraint_Nombre() { // test contraint nombre
        run(() ->this.dao.guardar(this.pejelagarto));
        Especie pejelagarto2 = new Especie("pejelagarto",CHOCOLATE,1,1,0);
        pejelagarto2.setPeso(151);
        pejelagarto2.setAltura(1980);
        pejelagarto2.setCantidadBichos(1);
        run(() ->this.dao.guardar(pejelagarto2));
    }
}
