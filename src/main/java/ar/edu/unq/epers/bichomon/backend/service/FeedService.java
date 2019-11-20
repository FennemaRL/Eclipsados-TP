package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB.MongoEventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionNeoDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;

public class FeedService implements Oyente {


    private final EntrenadorService ents;
    private final UbicacionNeoDao neodao;
    private MongoEventoDAO daoM;

    public FeedService(MongoEventoDAO mongoEventoDAO, List<Observado> obs, UbicacionNeoDao neodao, EntrenadorService ents) {
        daoM = mongoEventoDAO;
        obs.forEach(observado -> observado.agregarOyente(this));
        this.ents = ents;
        this.neodao = neodao;
    }

    public List<Evento> feedEntrenador(String entrenador){
        return runInSession(()->daoM.getByEntrenador(entrenador));
    }
    public List<Evento> feedUbicacion(String entrenador){
        Entrenador ent = ents.recuperar(entrenador);
        List<String> ubicaciones1 = neodao.conectadosp2pnombresytodosLosMedios(ent.getUbicacion());
        ArrayList<String> ubicaciones = new ArrayList<>();
        ubicaciones.addAll(ubicaciones1);
        ubicaciones.add(ent.getUbicacion().toString());
        return runInSession(()->daoM.feedUbicacionallorder(entrenador,ubicaciones));
    }

    @Override
    public void nuevoEvento(Evento ev) {
        runInSession(()->daoM.save(ev));

    }
}
