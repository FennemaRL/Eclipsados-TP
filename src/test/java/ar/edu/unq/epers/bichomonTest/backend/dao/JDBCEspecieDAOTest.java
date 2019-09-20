package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.JDBCEspecieDAOError;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;

import static org.junit.Assert.*;

public class JDBCEspecieDAOTest {

    private JDBCEspecieDAO dao = new JDBCEspecieDAO();
    private Especie pejelagarto;
    private DataServiceImp data=new DataServiceImp(dao);

    @Before
    public void crearModelo() {
        this.pejelagarto = new Especie("pejelagarto",CHOCOLATE);
        this.pejelagarto.setPeso(15);
        this.pejelagarto.setAltura(198);
        this.pejelagarto.setCantidadBichos(0);
        this.dao.restart();

    }
    @After
    public void restarModelo(){
        this.dao.restart();
    }


    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() { //test favorable guardar/recuperar
        this.dao.guardar(this.pejelagarto);

        Especie otraMismaEspecie= this.dao.recuperar("pejelagarto");

        assertEquals(this.pejelagarto.getNombre(), otraMismaEspecie.getNombre());
        assertEquals(this.pejelagarto.getPeso(), otraMismaEspecie.getPeso());
        assertEquals(this.pejelagarto.getAltura(), otraMismaEspecie.getAltura());
        assertEquals(this.pejelagarto.getCantidadBichos(), otraMismaEspecie.getCantidadBichos());
        assertTrue(this.pejelagarto != otraMismaEspecie);
    }

    @Test
    public void al_actualizar_los_objetos_son_similares() { // test favorable actualizar
        data.crearSetDatosIniciales();

        Especie otraMismaEspecie= this.dao.recuperar("Rojomon");

        otraMismaEspecie.setPeso(20);
        otraMismaEspecie.setAltura(400);

        this.dao.actualizar(otraMismaEspecie);
        Especie otraMismaEspecie1= this.dao.recuperar("Rojomon");

        assertEquals(otraMismaEspecie.getNombre(), otraMismaEspecie1.getNombre());
        assertEquals(otraMismaEspecie.getPeso(), otraMismaEspecie1.getPeso());
        assertEquals(otraMismaEspecie.getAltura(), otraMismaEspecie1.getAltura());
        assertEquals(otraMismaEspecie.getCantidadBichos(), otraMismaEspecie1.getCantidadBichos());
    }
    @Test(expected= JDBCEspecieDAOError.class)
    public void al_actualizar_id_inexistente_no_actualiza_y_levanta_excepcion() { // test desfavorable actualizar

        this.dao.actualizar(this.pejelagarto);
    }


    @Test(expected = JDBCEspecieDAOError.class)
    public void al_recuperar_un_nombre_inexistente_no_recupera_y_levanta_excepcion(){ // test desfavorable recuperar
        data.crearSetDatosIniciales();
        this.dao.recuperar("pejelagarto3");

    }

    @Test (expected=JDBCEspecieDAOError.class)
    public void al_guardar_dos_objetos_con_el_mismo_nombre_levanta_excepcion_por_constraint_Nombre() { // test contraint nombre
        data.crearSetDatosIniciales();

        Especie pejelagarto2 = new Especie("Rojomon",CHOCOLATE);
        pejelagarto2.setPeso(151);
        pejelagarto2.setAltura(1980);
        pejelagarto2.setCantidadBichos(1);
        this.dao.guardar(pejelagarto2);
    }
    @Test
    public void al_recuperarTodo_recupero_las_especies_guardadas_en_orden_alfabetico(){ // test recuperartodo favorable
        Especie rFort = new Especie("fortmon",CHOCOLATE);
        rFort.setPeso(110);
        rFort.setAltura(100);
        rFort.setCantidadBichos(1);

        this.dao.guardar(pejelagarto);
        this.dao.guardar(rFort);
        Especie ricky = this.dao.recuperarTodos().get(0);
        Especie peje = this.dao.recuperarTodos().get(1);

        assertEquals(ricky.getNombre(), rFort.getNombre());
        assertEquals(peje.getNombre(), pejelagarto.getNombre());
    }
    @Test
    public void al_recuperartodo_recupero_una_lista_vacia_por_que_no_hay_nada_guardado(){ // test recuperartodo desfavorable
        List<Especie> eList = new ArrayList<>();
        assertEquals(eList, dao.recuperarTodos());
    }

}