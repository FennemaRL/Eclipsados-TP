package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;

import javax.persistence.EntityNotFoundException;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
//Modificar daos, no existe entrenador, no exisste ubicacion, no existe bicho
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
        Entrenador  entre = recuperarEntrenador(entrenador);
        bicho = entre.capturar();//romper si tengo mas de los que puedo
        actualizarEntrenador(entre);
       return bicho;
    }

    private Entrenador recuperarEntrenador(String entrenador) { // Entrenador service
        try {
            Entrenador resultante = run(() -> this.entrenadorDAO.recuperar(entrenador));
            return resultante;
        } catch (Exception e) {
            throw new NoHayEntrenadorConEseNombre("no hay entrenador con ese nombre");
        }
    }

    public boolean puedeEvolucionar(String entrenador, Integer bicho){
        Entrenador entrenador1 =  recuperarEntrenador(entrenador);
        Bicho elBicho = entrenador1.getBichoConID(bicho);
        return (elBicho.puedeEvolucionar());
    }

    public Bicho evolucionar(String entrenador, int bicho) {
        Entrenador entre = recuperarEntrenador(entrenador);
        Bicho bichoo= entre.getBichoConID(bicho);
        bichoo.evolucionar();
        entre.aumentarExpPorEvolucionar();
        actualizarEntrenador(entre);
        return bichoo;
    }

    public void abandonarBicho(String entrenador, Integer bicho){
        Entrenador entrenador1 = recuperarEntrenador(entrenador);
        entrenador1.abandonarBicho(bicho);
        actualizarEntrenador(entrenador1);

    }

    public ResultadoCombate duelo(String entrenador, int bichoid){
        Entrenador entrenador1 = recuperarEntrenador(entrenador);
        ResultadoCombate rc = entrenador1.duelear(bichoid);
        actualizarEntrenador(entrenador1);
        return rc;
    }
    private void actualizarEntrenador(Entrenador entrenador) {
        run(()-> entrenadorDAO.actualizar(entrenador));
    }
}
