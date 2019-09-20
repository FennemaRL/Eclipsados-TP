package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;

public class BichoServiceTest {

    private HibernateEntrenadorDao entrenadorDAO = new HibernateEntrenadorDao();
    private HibernateEspecieDao especieDao = new HibernateEspecieDao();
    private HibernateBichoDao bichoDao = new HibernateBichoDao();
    private BichoService service ;
    private Entrenador lukas;
    private Especie chokomon;
    private Bicho leo;
    private Ubicacion guard;

    @Before
    public void initialize(){
        guard = new Guarderia("1114");
        lukas = new Entrenador( "Lukas",guard);
        chokomon = new Especie("chocoMon",CHOCOLATE);
        leo = new Bicho(chokomon);
        leo.setEnergia(20);
        lukas.agregarBichomon(leo);
        chokomon.setCondicionesEvolucion(10,0,1, DateTime.parse("2020-11-10"));

        service = new BichoService(entrenadorDAO, especieDao, bichoDao);

    }

    @Test()
    public void entrenador_busca_en_una_guarderia_sin_bichomones(){
        service.guardarEntrenador(lukas);
    }




}
