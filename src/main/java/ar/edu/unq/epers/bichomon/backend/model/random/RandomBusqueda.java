package ar.edu.unq.epers.bichomon.backend.model.random;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface RandomBusqueda  {
    boolean busquedaExitosa(Entrenador e);

    Especie especiePorProbabilidad(List<Especie> especiesUbicacion,List<Integer> probabilidad);
            //prec : la lista lb y la pb estan relacionadas donde cada posicion de una Especie tiene la misma posicion para su probabilidad de aparicion
            //          No puede haber bichos sin probabilidad ni viceversa y suma de todas las prioridades tiene que ser siempre 100)
}
