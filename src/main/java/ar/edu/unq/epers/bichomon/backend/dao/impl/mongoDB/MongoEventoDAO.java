package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;

import java.util.List;

public class MongoEventoDAO extends GenericMongoDao<Evento> {
    public MongoEventoDAO() {
        super(Evento.class);
    }
    public List<Evento> getByEntrenador(String entrenador) {
        return this.find("{ entrenador: # }", entrenador);
    }
}
