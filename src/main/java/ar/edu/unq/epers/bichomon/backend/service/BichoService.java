package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class BichoService {

    private HibernateEntrenadorDao entrenadorDAO;

    private HibernateEspecieDao especieDao;

    private HibernateBichoDao bichoDao;

    public BichoService(HibernateEntrenadorDao entrenadorDAO, HibernateEspecieDao especieDao, HibernateBichoDao bichoDao) {
        this.entrenadorDAO = entrenadorDAO;
        this.especieDao = especieDao;
        this.bichoDao = bichoDao;
    }

    public Bicho buscar(String entrenador){

        Bicho bicho = null;
        Entrenador e;
        try{
            e = recuperarEntrenador(entrenador);}
        catch (ErrorRecuperar error) {
            throw new NoHayEntrenadorConEseNombre("no hay entrenador con ese nombre");
            }
        Entrenador entre =  e;
        bicho = entre.capturar();//romper si tengo mas de los que puedo
       return bicho;
    }

    private Entrenador recuperarEntrenador(String entrenador) {
        return run(() -> this.entrenadorDAO.recuperar(entrenador));
    }

    public boolean puedeEvolucionar(String entrenador, Integer bicho){
        Entrenador entrenador1 = (Entrenador) entrenadorDAO.recuperar(entrenador);
        Bicho elBicho = entrenador1.getBichoConID(bicho);
        return (elBicho.puedeEvolucionar());
    }

    public void guardarEntrenador(Entrenador entre) {
        run(() -> this.entrenadorDAO.guardar(entre));
    }

    public void guardarEspecie(Especie esp) {
        run(() -> this.especieDao.guardar(esp));
    }

    public void abandonarBicho(String entrenador, Integer bicho){
        Entrenador entrenador1 = recuperarEntrenador(entrenador);
        entrenador1.abandonarBicho(bicho);

    }
}
