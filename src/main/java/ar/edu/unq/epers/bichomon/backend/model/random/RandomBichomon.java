package ar.edu.unq.epers.bichomon.backend.model.random;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
@Entity
public class RandomBichomon  {
    @Id
    @GeneratedValue
    private int id;
    public RandomBichomon(){
    }
    public boolean busquedaExitosa(Entrenador e, Ubicacion u) {
        return false;
    }

    public Especie especiePorProbabilidad(List<Especie> especiesUbicacion, List<Integer> probabilidad) {
        return null;
    }
}
