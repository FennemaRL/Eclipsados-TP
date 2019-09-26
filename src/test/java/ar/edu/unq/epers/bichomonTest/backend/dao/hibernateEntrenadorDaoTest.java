package ar.edu.unq.epers.bichomonTest.backend.dao;


import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.ErrorRecuperar;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.Date;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
import static org.junit.Assert.assertEquals;

public class hibernateEntrenadorDaoTest { //falta testear cosas y agregar constrains pero lo basico de guardar
    private HibernateEntrenadorDao dao= new HibernateEntrenadorDao();
    private Entrenador pepe;
    private Ubicacion ubi;

    @Before
    public void crearModelo() {
        ubi = new Guarderia("1114");
        pepe = new Entrenador("pepe", ubi);
        Bicho b = new Bicho(new Especie("arnaldo", TipoBicho.AGUA,1,1,0));
        b.setFechaCaptura(new Date());
        pepe.agregarBichomon(b);
    }
    @Test
    public void test_al_guardar_y_luego_recuperar_se_obtiene_objetos_similares(){

        run(() ->this.dao.guardar(pepe)); // modificar la fecha manaña por que es demasiado larga :S

        Entrenador pep2 = run(() ->this.dao.recuperar("pepe"));

        assertEquals(pepe.getUbicacion().getNombreUbicacion(), pep2.getUbicacion().getNombreUbicacion());
        assertEquals(pepe.getNombre(), pep2.getNombre());
    }
    @Test(expected = ErrorRecuperar.class)
    public void al_recuperar_un_nombre_inexistente_no_recupera_y_levanta_excepcion(){ // test desfavorable recuperar
        run(() ->this.dao.recuperar("pejelagarto3"));
    }

    @Test (expected= PersistenceException.class) //arreglar en clase
    public void al_guardar_dos_objetos_con_el_mismo_nombre_levanta_excepcion_por_constraint_Nombre() { // test contraint nombre
        run(() ->this.dao.guardar(this.pepe));
        Entrenador et = new Entrenador("pepe",ubi);
        run(() ->this.dao.guardar(et));
    }
}
