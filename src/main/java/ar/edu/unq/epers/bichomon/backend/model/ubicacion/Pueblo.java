package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBusqueda;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Entity

public class Pueblo extends Ubicacion{

    @ManyToOne ( cascade = CascadeType.ALL)
    private RandomBichomon random;

    @ManyToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<EspecieConProv> especies;

    private int cantEntrenadores;
    public Pueblo(){}
    public Pueblo(String name,RandomBichomon rb) {
        super(name);
        random = rb;
    }

    public Pueblo(String s, RandomBichomon r, List<EspecieConProv> le ) {
        super(s);
        especies = new LinkedHashSet<>();
        especies.addAll(le);
        cantEntrenadores=0;
        random =r;
    }

    public Pueblo(String nombre) {
        this.nombreUbicacion =nombre;
    }

    @Override
    public Bicho capturar(Entrenador e) {
        Bicho br =null;
        Boolean resultadoCaptura=random.busquedaExitosa(e, this);
        if(resultadoCaptura){
            Especie esp = especiePorProbabilidad();
            br = new Bicho(esp);
        }
        if (!resultadoCaptura){
            throw new CapturaFallida("no se capturo nada");
        }
        return br;
    }

    private Especie especiePorProbabilidad() {
        int random  = (int) (Math.random() * (101 - 0));
        Map<Integer,Especie> probEspecie= new HashMap<>();
        int total = 1;
        for(EspecieConProv esp : especies){
            for(int i = 1 ; i < esp.getProv() ; i++){
                probEspecie.put(total,esp.getEsp());
                total++;
            }
        }
        return probEspecie.get(random);
    }

    @Override
    public String getEntrenadorName() {
        return null;
    }

    @Override
    public String getBichomonName() {
        return null;
    }


}
