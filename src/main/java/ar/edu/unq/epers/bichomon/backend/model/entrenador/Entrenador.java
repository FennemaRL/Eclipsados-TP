package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Entrenador {
    @Id
    @GeneratedValue
    private int id;
    @Column (unique = true)
    private String nombre;
    private Integer experiencia;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichos;
    @ManyToOne(cascade = CascadeType.ALL)
    private ExperienciaValor expGen;
    @ManyToOne(cascade = CascadeType.ALL)
    private Nivel nivelGen;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    public Entrenador(){}
    public Entrenador(int id, String nombre, Integer exp, Integer lvl){
        this.id= id;
        this.nombre = nombre;
        this.experiencia= exp;
        bichos = new ArrayList<Bicho>();
    }
    public Entrenador( String nombre, Ubicacion u){
        this.nombre = nombre;
        this.experiencia= 0;
        bichos = new ArrayList<Bicho>();
        ubicacion = u;
    }

    public Entrenador(String nombre, Ubicacion ubicacion, int experiencia ,ExperienciaValor expGen, Nivel nivelGen) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.experiencia = experiencia;
        //this.expGen = expGen;
        //this.nivelGen = nivelGen;
        bichos = new ArrayList<Bicho>();

    }

    public Bicho getBichoConID(Integer bichoId) {

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



    public Integer getNivel() { return  nivelGen.getNivel(this.experiencia); }

    public boolean tieneBichoConId(Integer bichoId){
        return bichos.stream().filter(b-> b.getId() == bichoId).findAny().orElse(null) != null;

    }


    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion){this.ubicacion = ubicacion;}

    public String getNombre(){return nombre;}

    public String toString(){
        final String[] b = {" "};
        bichos.forEach(bicho -> b[0] = b[0] + bicho.toString()+ " ");
        return( nombre + " id: "+id +"{"+ b[0] +"}");
    }

    public ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate duelear(int idBicho){
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
        if(false )//nivelGen.soyNivelMaximo(experiencia))
            return true;
        else{
            return tengoCantidadMaximaPorNivel();
        }
    }

    private boolean tengoCantidadMaximaPorNivel() {
        int cantBichos =bichos.size();
        switch (1){//nivelGen.getNivel(experiencia)){
            case 1 : return cantBichos == 2;
            case 2 : return cantBichos == 3;
            case 3 : return cantBichos == 4;
            case 4 : return cantBichos == 5;
            default:  return cantBichos == 6;
        }
    }

    public List<Bicho> getBichos(){
        return this.bichos;
    }


    public void abandonarBicho(Integer bichoId){
        Bicho bicho = this.getBichoConID(bichoId);
        if(this.bichos.size()>=2){
            ubicacion.adoptar(bicho);
            bicho.abandonar();
            bichos.remove(bicho);
        }
        else{
            throw new BichomonError("Entrenador no puede quedarse sin bichos");
        }

    }

    public void aumentarExpPorCombate() {
        //this.experiencia += expGen.getPuntosCombatir();
    }
    private void aumentarExpPorCapturar(){}//this.experiencia += expGen.getPuntosCapturar();}
    private void aumentarExpPorEvolucionar(){}//this.experiencia += expGen.getPuntosEvolucionar();}


    public void setExperienciaValor(ExperienciaValor expGen){
        //this.expGen = expGen;
    }

    public void setNivelGen(Nivel nivelGen) {
        //this.nivelGen = nivelGen;
    }
}
