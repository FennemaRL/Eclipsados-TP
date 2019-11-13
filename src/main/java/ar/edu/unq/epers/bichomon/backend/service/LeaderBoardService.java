package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateUbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieNoExistente;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;

public class LeaderBoardService {

    private HibernateUbicacionDao dao;

    public LeaderBoardService(HibernateUbicacionDao dao){
        this.dao = dao;
    }

    public List<Entrenador> campeones(){
        return TransactionRunner.runInSession( ()-> dao.campeones());

    }

    public Especie especieLider() {
        Especie esp = TransactionRunner.runInSession(() -> dao.especieLider());
       if( esp == null)
            throw new EspecieNoExistente("no hay especie lider por el momento");
       else
           return esp;
    }

    public List<Entrenador> lideres() {
        return TransactionRunner.runInSession(()-> dao.lideres());
    }
}
