package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Entrenador {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column (unique = true)
    private String nombre;
    private Integer experiencia;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Bicho> bichos;

    @ManyToOne(cascade = CascadeType.ALL)
    private ExperienciaValor expGen;

    @ManyToOne(cascade = CascadeType.ALL)
    private NivelEntrenador nivelEntrenadorGen;

    @ManyToOne(cascade = CascadeType.ALL)
    //@Transient
    private Ubicacion ubicacion;

    private int dollars = 0;

    public Entrenador(){}
    public Entrenador(int id, String nombre, Integer exp){
        this.id= id;
        this.nombre = nombre;
        this.experiencia= exp;
        bichos = new HashSet<>();
    }
    public Entrenador( String nombre, Ubicacion u){
        this.nombre = nombre;
        this.experiencia= 0;
        bichos = new HashSet<>();
        ubicacion = u;
        u.incrementarCantidadDeEntrenadores();
    }

    public Entrenador(String nombre, Ubicacion ubicacion, ExperienciaValor expGen, NivelEntrenador nivelEntrenadorGen) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.experiencia = 1;
        this.expGen = expGen;
        this.nivelEntrenadorGen = nivelEntrenadorGen;
        bichos = new HashSet<>();
        ubicacion.incrementarCantidadDeEntrenadores();
    }

    public Bicho getBichoConID(long bichoId) {

        Bicho bichoEncontrar = bichos.stream().filter(b-> b.getId() == bichoId).findAny().orElse(null) ;

        if (bichoEncontrar == null){
            throw new EntrenadorException(this, bichoId);
        }
        return bichoEncontrar;
    }

    public void agregarBichomon(Bicho unBicho) {
        unBicho.setFechaCaptura(new Date());
        unBicho.setOwner(this);
        bichos.add(unBicho);
    }



    public Integer getNivel() { return  nivelEntrenadorGen.getNivel(this.experiencia); }

    public boolean tieneBichoConId(Integer bichoId){
        return bichos.stream().filter(b-> b.getId() == bichoId).findAny().orElse(null) != null;

    }


    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion){
        this.ubicacion.decrementarCantidadDeEntrenadores();
        this.ubicacion = ubicacion;
        this.ubicacion.incrementarCantidadDeEntrenadores();
    }

    public String getNombre(){return nombre;}

    public String toString(){
        final String[] b = {" "};
        bichos.forEach(bicho -> b[0] = b[0] + bicho.toString()+ " ");
        return( "Entrenador :"+nombre + " id: "+id +"{"+ b[0] +"}");
    }

    public ResultadoCombate duelear(long idBicho){
        return ubicacion.retar(this, getBichoConID(idBicho));
    }

    public Bicho capturar() {
        if(haveMaxCantBichos()){
            throw new BichomonError("No Puedo Capturar mas poquemon");
        }
        Bicho nuevoBicho = ubicacion.capturar(this);
        nuevoBicho.setFechaCaptura(new Date());
        nuevoBicho.setOwner(this);
        this.agregarBichomon(nuevoBicho);
        this.aumentarExpPorCapturar();
        return nuevoBicho;
    }

    private boolean haveMaxCantBichos() {
        if(nivelEntrenadorGen.soyNivelMaximo(experiencia))
            return true;
        else{
            return tengoCantidadMaximaPorNivel();
        }
    }

    private boolean tengoCantidadMaximaPorNivel() {
        int cantBichos =bichos.size();
        switch (nivelEntrenadorGen.getNivel(experiencia)){
            case 1 : return cantBichos == 2;
            case 2 : return cantBichos == 3;
            case 3 : return cantBichos == 4;
            case 4 : return cantBichos == 5;
            default:  return cantBichos == 6;
        }
    }

    public Set<Bicho> getBichos(){
        return this.bichos;
    }


    public void abandonarBicho(Integer bichoId){
        Bicho bicho = this.getBichoConID(bichoId);
        if(this.bichos.size()>=2){
            ubicacion.adoptar(bicho);
            bichos.remove(bicho);
        }
        else{
            throw new BichomonError("Entrenador no puede quedarse sin bichos");
        }

    }

    public void aumentarExpPorCombate() {this.experiencia += expGen.getPuntosCombatir();}
    private void aumentarExpPorCapturar(){this.experiencia += expGen.getPuntosCapturar();}
    public void aumentarExpPorEvolucionar(){this.experiencia += expGen.getPuntosEvolucionar();}


    public void setExperienciaValor(ExperienciaValor expGen){this.expGen = expGen; }

    public void setNivelGen(NivelEntrenador nivelEntrenadorGen) {this.nivelEntrenadorGen = nivelEntrenadorGen;  }

    public ResultadoCombate duelear() {
        return ubicacion.retar(this, bichos.stream().collect(Collectors
                .toCollection(ArrayList::new)).get(0) );

    }
    @Override
    public boolean equals(Object o ){
        if(o== null ||(o.getClass() != this.getClass()))
            return false;
        else
            return ((Entrenador )o).getId() == id ;
    }

    private long getId() {
        return id;
    }
    public void setBichoDollars(int i){dollars = i;}
    public void pagar(int i){
        if(dollars < i)
            throw new BichomonError("El entrenador no posee esa cantidad de dollars");
        else
            dollars -=i;
    }
    public int cantBichoDollars() {
        return dollars;
    }
}
