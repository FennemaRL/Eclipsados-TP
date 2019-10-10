package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;


import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class UbicacionService {
    private HibernateUbicacionDao dao;
    public UbicacionService(HibernateUbicacionDao hibernateUbicacionDao){dao =hibernateUbicacionDao;}

    public Ubicacion recuperar(String ubicacion) {
            return run(() -> this.dao.recuperarUbicacion(ubicacion));
    }

    public void guardar(Ubicacion ubicacion){
        run(()-> dao.guardar(ubicacion));
    }
    public void actualizar(Ubicacion ubicacion){run(()-> dao.actualizar(ubicacion));
    }

    public Bicho campeonHistorico(String dojo) {
        return run(()-> dao.campeonHistorico(dojo));
    }
}
