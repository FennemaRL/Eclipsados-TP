package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayUbicacionConEseNombre;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;


import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;

public class UbicacionService {
    private HibernateUbicacionDao dao;
    public UbicacionService(HibernateUbicacionDao hibernateUbicacionDao){dao =hibernateUbicacionDao;}

    public Ubicacion recuperar(String ubicacion) {
        try {
            return TransactionRunner.runInSession(() -> this.dao.recuperarUbicacion(ubicacion));
        } catch (Exception e) {
            throw new NoHayUbicacionConEseNombre("no hay ubicacion con ese nombre");
        }
    }

    public void guardar(Ubicacion ubicacion){
        runInSession(()-> dao.guardar(ubicacion));
    }
    public void actualizar(Ubicacion ubicacion){
        runInSession(()-> dao.actualizar(ubicacion));
    }

    public Bicho campeonHistorico(String dojo) {
        return TransactionRunner.runInSession(()-> dao.campeonHistorico(dojo));
    }

}
