package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import ar.edu.unq.epers.bichomon.backend.service.Evento;
import org.jongo.MongoCursor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MongoEventoDAO extends GenericMongoDao<Evento> {
    public MongoEventoDAO() {
        super(Evento.class);
    }
    public List<Evento> getByEntrenador(String entrenador) {
        return this.find("{ entrenador: # }", entrenador);
    }

    public List<Evento> feedUbicacionallorder(String entrenador,List<String> Ubicaciones) {
        try {
            MongoCursor<Evento> all = this.mongoCollection.find("{ entrenador: # }", entrenador).sort("{date:-1}").as(Evento.class);
            List<Evento> result = this.copyToList(all);
            all.close();
            return result.stream().filter(evento ->Ubicaciones.contains(evento.getnombreub())).collect(Collectors.toList());
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
