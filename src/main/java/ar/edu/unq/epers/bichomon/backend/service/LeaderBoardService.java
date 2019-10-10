package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class LeaderBoardService {

    private HibernateUbicacionDao dao;

    public LeaderBoardService(HibernateUbicacionDao dao){
        this.dao = dao;
    }

    public List<Entrenador> campeones(){
        return run( ()-> dao.campeones());

    }
}
