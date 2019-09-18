package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBusqueda;

import java.util.List;

public class Pueblo extends Ubicacion{

    private RandomBusqueda random;
    private List<Especie>  especies;
    private List<Integer> especiesProbabilidad;

    public Pueblo(String name) {
        super(name);
    }

    public Pueblo(String s, RandomBusqueda r, List<Especie> le, List ep) {
        //prec : la lista lb y la pb estan relacionadas donde cada posicion de una Especie tiene la misma posicion para su probabilidad de aparicion
        //          No puede haber bichos sin probabilidad ni viceversa y suma de todas las prioridades tiene que ser siempre 100)
        super(s);
        random = r;
        especies= le;
        especiesProbabilidad = ep;
    }

    @Override
    public Bicho capturar(Entrenador e) {
        Bicho br =null;
        if(random.busquedaExitosa(e)){
            Especie esp = random.especiePorProbabilidad(especies, especiesProbabilidad);
            br = new Bicho(esp);
            esp.incrementarEnUnBicho();
        }
        return br;

    }
}
