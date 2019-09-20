package ar.edu.unq.epers.test.modelo;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.junit.Test;

public class TestDao {

    @Test
    public void ttest(){
        HibernateEntrenadorDao dao = new HibernateEntrenadorDao();
        Entrenador e = new Entrenador();
        TransactionRunner.run(() -> dao.guardar(e));
    }
}
