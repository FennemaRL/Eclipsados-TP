package ar.edu.unq.epers.bichomon.backend.model.random;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.EspecieConProv;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RandomBichomon  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected long id;

    public RandomBichomon(){
    }
    public boolean busquedaExitosa(Entrenador e, Ubicacion u) {
        return true;
    }

    public Especie especiePorProbabilidad(List<EspecieConProv> le) {
        return null;
    }
}
