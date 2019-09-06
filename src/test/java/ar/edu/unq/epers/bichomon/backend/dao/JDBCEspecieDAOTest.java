package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static org.junit.Assert.*;

public class JDBCEspecieDAOTest {

    private EspecieDAO dao = new JDBCEspecieDAO();
    private Especie pejelagarto;

    @Before
    public void crearModelo() {
        this.pejelagarto = new Especie(0,"pejelagarto",CHOCOLATE);
        this.pejelagarto.setPeso(15);
        this.pejelagarto.setAltura(198);
        this.pejelagarto.setCantidadBichos(0);
    }


    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
        this.dao.guardar(this.pejelagarto);
        //Los personajes son iguales
        Especie otraMismaEspecie= this.dao.recuperar("pejelagarto");
        assertEquals(this.pejelagarto.getNombre(), otraMismaEspecie.getNombre());
        assertEquals(this.pejelagarto.getPeso(), otraMismaEspecie.getPeso());
        assertEquals(this.pejelagarto.getAltura(), otraMismaEspecie.getAltura());
        assertEquals(this.pejelagarto.getCantidadBichos(), otraMismaEspecie.getCantidadBichos());

        assertTrue(this.pejelagarto != otraMismaEspecie);
    }
    @Test
    public void al_actualizar_los_objetos_son_similares() {
        this.dao.guardar(this.pejelagarto);
        this.pejelagarto.setPeso(20);
        this.pejelagarto.setAltura(400);

        //Los personajes son iguales

        Especie otraMismaEspecie= this.dao.recuperar("pejelagarto");
        assertEquals(this.pejelagarto.getNombre(), otraMismaEspecie.getNombre());
        assertFalse(this.pejelagarto.getPeso() == otraMismaEspecie.getPeso());
        assertFalse(this.pejelagarto.getAltura() ==otraMismaEspecie.getAltura());
        assertEquals(this.pejelagarto.getCantidadBichos(), otraMismaEspecie.getCantidadBichos());

        assertTrue(this.pejelagarto != otraMismaEspecie);
        System.out.println("altura peso "+this.pejelagarto.getAltura()+this.pejelagarto.getPeso());

        this.dao.actualizar(this.pejelagarto);

         Especie otraMismaEspecie1= this.dao.recuperar("pejelagarto");
        assertEquals(this.pejelagarto.getNombre(), otraMismaEspecie1.getNombre());
        assertEquals(this.pejelagarto.getPeso(), otraMismaEspecie1.getPeso());
        assertEquals(this.pejelagarto.getAltura(), otraMismaEspecie1.getAltura());
        assertEquals(this.pejelagarto.getCantidadBichos(), otraMismaEspecie1.getCantidadBichos());
    }
    @Test
    public void al_actualizar_id_inexistente_no_actualiza() {
        this.dao.actualizar(this.pejelagarto);

    }

}