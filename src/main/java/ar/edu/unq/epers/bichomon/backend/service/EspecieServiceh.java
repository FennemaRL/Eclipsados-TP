package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class EspecieServiceh {
    private HibernateEspecieDao dao;
    public EspecieServiceh(HibernateEspecieDao daoesp) {
        dao = daoesp;
    }

    public List<Especie> populares() {
        return run(()-> dao.populares());
    }
}
