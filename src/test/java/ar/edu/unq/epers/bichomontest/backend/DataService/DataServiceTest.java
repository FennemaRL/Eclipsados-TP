package ar.edu.unq.epers.bichomontest.backend.DataService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAOError;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;

public class DataServiceTest {
    private JDBCEspecieDAO dao = new JDBCEspecieDAO();
    private DataService ds = new DataServiceImp(dao);
    private Especie pejelagarto ;
    private Especie rojo ;

    @Before
    public void crearModelo() {
        this.pejelagarto = new Especie(1,"pejelagarto",CHOCOLATE);
        this.pejelagarto.setPeso(15);
        this.pejelagarto.setAltura(198);
        this.pejelagarto.setCantidadBichos(0);
        this.rojo = new Especie(0,"rojo",TipoBicho.FUEGO);
    }
    @Test(expected= JDBCEspecieDAOError.class)
    public void al_guardar_un_objeto_con_el_mismo_de_otro_creado_por_el_Data_Service_Levanta_una_excepcion(){
        this.ds.crearSetDatosIniciales();
        this.dao.guardar(pejelagarto);
    }
    
}