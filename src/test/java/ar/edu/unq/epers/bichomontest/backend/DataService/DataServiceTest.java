package ar.edu.unq.epers.bichomontest.backend.DataService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAOError;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DataServiceTest {
    private JDBCEspecieDAO dao = new JDBCEspecieDAO();
    private DataService ds = new DataServiceImp(dao);

    @Before
    public void reset(){
        ds.eliminarDatos();
    }
    @Test
    public void al_cargar_datos_por_el_Data_Service(){ // test favorable cargar datos/ borrar datos
        this.ds.crearSetDatosIniciales();
        List<Especie> eList = new ArrayList<>();
        assertFalse(eList.equals(dao.recuperarTodos()));

        ds.eliminarDatos();

        assertEquals(eList,dao.recuperarTodos());
    }
    @Test (expected = JDBCEspecieDAOError.class) //test desfavorable
    public void levanta_excepcion_al_tratar_de_cargar_datos_Con_el_Data_service_al_haber_una_especie_con_el_mismo_Id(){
        this.dao.guardar(new Especie(1,"toco", TipoBicho.ELECTRICIDAD));
        this.ds.crearSetDatosIniciales();
    }
    @Test
    public void borrar_datos_cuando_no_hay_datos_inicializados(){ // test favorable datos
        this.ds.eliminarDatos();
        List<Especie> eList = new ArrayList<>();
        assertEquals(eList, dao.recuperarTodos() );
    }
    
}