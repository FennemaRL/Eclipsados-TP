package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayEntrenadorConEseNombre;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class EntrenadorService {
    private HibernateEntrenadorDao dao;
    public EntrenadorService(HibernateEntrenadorDao hibernateEntrenadorDao) {
        dao = hibernateEntrenadorDao;
    }

    public Entrenador recuperar(String entrenador) {
            Entrenador entrenador1 = run(() -> this.dao.recuperar(entrenador));
            if (entrenador1 == null){
                throw new NoHayEntrenadorConEseNombre("no hay entrenador con ese nombre");
            }
            return entrenador1;
    }
    public void guardar(Entrenador entrenador){
        run(()-> dao.guardar(entrenador));
    }
    public void actualizar(Entrenador entrenador){
        run(()-> dao.actualizar(entrenador));
    }

}
