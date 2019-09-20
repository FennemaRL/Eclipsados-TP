package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class BichoService {

    private HibernateDAO entrenadorDAO;

    private HibernateDAO especieDao;

    private HibernateDAO bichoDao;

    public BichoService(HibernateEntrenadorDao entrenadorDAO, HibernateEspecieDao especieDao, HibernateBichoDao bichoDao) {
        this.entrenadorDAO = entrenadorDAO;
        this.especieDao = especieDao;
        this.bichoDao = bichoDao;
    }

    public Bicho buscar(String entrenador){

        Bicho bicho = null;
        Object e = entrenadorDAO.recuperar(entrenador);
        System.out.print(e);
        if(e == null)
            throw new NoHayEntrenadorConEseNombre("no hay entrenador con ese nombre");
        Entrenador entre = (Entrenador) e;
        if(entre.puedeCapturar()){
            try{
            bicho = entre.getUbicacion().capturar(entre);
            entre.agregarBichomon(bicho); //romper si tengo mas de los que puedo
            }
            catch (BichomonError error) {}
       }
       return bicho;
    }

    public boolean puedeEvolucionar(String entrenador, Integer bicho){
        Entrenador entrenador1 = (Entrenador) entrenadorDAO.recuperar(entrenador);
        Bicho elBicho = entrenador1.getBichoConID(bicho);
        return (elBicho.puedeEvolucionar());
    }

    public void guardarEntrenador(Entrenador entre) {
        run(() -> this.entrenadorDAO.guardar(entre));
    }
}
