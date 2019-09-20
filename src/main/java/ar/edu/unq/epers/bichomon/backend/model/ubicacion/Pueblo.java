package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBusqueda;

import javax.persistence.*;
import java.util.List;
@Entity
public class Pueblo extends Ubicacion{

    @Transient
    private RandomBusqueda random;
    @ManyToMany(cascade =  CascadeType.ALL)
    private List<Especie>  especies;
    @ElementCollection
    @CollectionTable(name="especiesProbabilidad", joinColumns=@JoinColumn(name="nombreUbicacion"))
    @Column(name="probabilidad")
    private List<Integer> especiesProbabilidad;
    private int cantEntrenadores;

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
        cantEntrenadores=0;
    }

    @Override
    public Bicho capturar(Entrenador e) {
        Bicho br =null;
        Boolean resultadoCaptura=random.busquedaExitosa(e, this);
        if(resultadoCaptura){
            Especie esp = random.especiePorProbabilidad(especies, especiesProbabilidad);
            br = new Bicho(esp);
            esp.incrementarEnUnBicho();
        }
        if (!resultadoCaptura){
            throw new CapturaFallida("no se capturo nada");
        }
        return br;
    }
    public Integer getCantEntrenadores(){
        return cantEntrenadores;
    }
}
