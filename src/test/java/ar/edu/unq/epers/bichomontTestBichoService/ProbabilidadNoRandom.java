package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.Entity;
@Entity
public class ProbabilidadNoRandom extends RandomBichomon {
    @Override
    public boolean busquedaExitosa(Entrenador e, Ubicacion u) {
        return true;
    }

}
