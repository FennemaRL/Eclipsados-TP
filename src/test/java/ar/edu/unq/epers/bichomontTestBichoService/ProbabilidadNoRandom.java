package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.Entity;
import java.util.List;
@Entity
public class ProbabilidadNoRandom extends RandomBichomon {
    @Override
    public boolean busquedaExitosa(Entrenador e, Ubicacion u) {
        return true;
    }

    @Override
    public Especie especiePorProbabilidad(List<Especie> especiesUbicacion, List<Integer> probabilidad) {
        return especiesUbicacion.get(0);
    }
}
