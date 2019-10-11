package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayEntrenadorConEseNombre;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayUbicacionConEseNombre;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;


import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class UbicacionService {
    private HibernateUbicacionDao dao;
    public UbicacionService(HibernateUbicacionDao hibernateUbicacionDao){dao =hibernateUbicacionDao;}

    public Ubicacion recuperar(String ubicacion) {
        try {
            return run(() -> this.dao.recuperarUbicacion(ubicacion));
        } catch (Exception e) {
            throw new NoHayUbicacionConEseNombre("no hay ubicacion con ese nombre");
        }
    }

    public void guardar(Ubicacion ubicacion){
        run(()-> dao.guardar(ubicacion));
    }
    public void actualizar(Ubicacion ubicacion){run(()-> dao.actualizar(ubicacion));
    }

    public Bicho campeonHistorico(String dojo) {
        return run(()-> dao.campeonHistorico(dojo));
    }

    public Bicho recuperarCampeon(Ubicacion ubi) {
        return run(()-> dao.campeonDojo(ubi));
    }
}
