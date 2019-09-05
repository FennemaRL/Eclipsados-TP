package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JDBCEspecieDAOTest {

    private JDBCEspecieDAO dao = new JDBCEspecieDAO();
    private Especie pejelagarto;
    private Especie pejelagarto2;

    @Before
    public void crearModelo() {
        this.pejelagarto = new Especie(0,"pejelagarto",CHOCOLATE);
        this.pejelagarto.setPeso(15);
        this.pejelagarto.setAltura(198);
        this.pejelagarto.setCantidadBichos(0);
        this.pejelagarto2 = new Especie(1,"pejelagarto",CHOCOLATE);
        this.pejelagarto.setPeso(151);
        this.pejelagarto.setAltura(1980);
        this.pejelagarto.setCantidadBichos(1);
    }
    @After
    public void restarModelo(){
        this.dao.restart();
    }


    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
        this.dao.guardar(this.pejelagarto);

        Especie otraMismaEspecie= this.dao.recuperar("pejelagarto");
        assertEquals(this.pejelagarto.getNombre(), otraMismaEspecie.getNombre());
        assertEquals(this.pejelagarto.getPeso(), otraMismaEspecie.getPeso());
        assertEquals(this.pejelagarto.getAltura(), otraMismaEspecie.getAltura());
        assertEquals(this.pejelagarto.getCantidadBichos(), otraMismaEspecie.getCantidadBichos());

        assertTrue(this.pejelagarto != otraMismaEspecie);
    }
    @Test (expected=RuntimeException.class)
    public void al_guardar_dos_veces_el_mismo_objeto_Levanta_excepcion_por_constraint_Id() {
        this.dao.guardar(this.pejelagarto);
        this.dao.guardar(this.pejelagarto);
    }
    @Test (expected=RuntimeException.class)
    public void al_guardar_dos_objetos_con_el_mismo_nombre_levanta_excepcion_por_constraint_Nombre() {
        this.dao.guardar(this.pejelagarto);
        this.dao.guardar(this.pejelagarto2);
    }
}