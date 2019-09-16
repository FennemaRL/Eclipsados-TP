package ar.edu.unq.epers.bichomonTest.backend.dao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.CHOCOLATE;

public class BichoServiceTest {

    private HibernateEntrenadorDao entrenadorDAO = new HibernateEntrenadorDao();
    private BichoService bichoService = new BichoService(entrenadorDAO);
    private Entrenador lukas;
    private Especie chokomon;
    private Bicho leo;

    @Before
    public void initialize(){

        lukas = new Entrenador( 1, "Lukas", 1, 1);
        chokomon = new Especie(1,"chocoMon",CHOCOLATE);
        leo = new Bicho(chokomon,"leo",1);
        leo.setEnergia(20);
        lukas.agregarBichomon(leo);
        chokomon.setCondicionesEvolucion(10,0,1, DateTime.parse("2020-11-10"));

        entrenadorDAO.guardar(lukas);


    }





}
