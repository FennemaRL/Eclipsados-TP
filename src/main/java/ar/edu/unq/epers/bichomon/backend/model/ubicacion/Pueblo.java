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
    private Set<Especie> especies;

    @ElementCollection
    @CollectionTable(name="especiesProbabilidad", joinColumns=@JoinColumn(name="nombreUbicacion"))
    @Column(name="probabilidad")
    private List<Integer> especiesProbabilidad;
    private int cantEntrenadores;
    public Pueblo(){}
    public Pueblo(String name,RandomBichomon rb) {
        super(name);
        random = rb;
    }

    public Pueblo(String s, RandomBichomon r, List<Especie> le, List ep) {
        //prec : la lista lb y la pb estan relacionadas donde cada posicion de una Especie tiene la misma posicion para su probabilidad de aparicion
        //          No puede haber bichos sin probabilidad ni viceversa y suma de todas las prioridades tiene que ser siempre 100)
        //          las probabilidades van en orden
        super(s);
        especies = new LinkedHashSet<>();
        especies.addAll(le);
        especiesProbabilidad = ep;
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
            List<Especie> espl  = new ArrayList<>();
            espl.addAll(especies);
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
        List<Especie> especies = this.especies.stream().collect(Collectors.toList());
        int cambioEspecie = 0;
        for(int i=0; i < especiesProbabilidad.get(cambioEspecie); i++){
            if(i> especiesProbabilidad.get(cambioEspecie))
                cambioEspecie += 1;
            probEspecie.put(i,especies.get(cambioEspecie));
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
