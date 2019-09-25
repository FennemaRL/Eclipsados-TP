package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
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
    private Integer nivel;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bicho> bichos;

    @ManyToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion;

    public Entrenador(){}
    public Entrenador(int id, String nombre, Integer exp, Integer lvl){
        this.id= id;
        this.nombre = nombre;
        this.experiencia= exp;
        this.nivel= lvl;
        bichos = new ArrayList<Bicho>();
    }
    public Entrenador( String nombre, Ubicacion u){
        this.nombre = nombre;
        this.experiencia= 0;
        this.nivel= 0;
        bichos = new ArrayList<Bicho>();
        ubicacion = u;
    }

    public Bicho getBichoConID(Integer bichoId) {
        //Bicho bichoEncontrar = bichos.stream().findAny(b-> b.getId() == bicho) ;
        Bicho bichoEncontrar = bichos.stream().filter(b-> b.getId() == bichoId).findAny().orElse(null) ;
        if (bichoEncontrar == null) throw new EntrenadorException(this, bichoId);
        return bichoEncontrar;
    }

    public void agregarBichomon(Bicho unBicho) {
        unBicho.setFechaCaptura(new Date());
        unBicho.setOwner(this);
        bichos.add(unBicho);
    }

    public boolean puedeEvolucionarBichoConID(Integer id) {
        return getBichoConID(id).puedeEvolucionar();
    }

    public Integer getNivel() { return this.nivel; }

    public boolean tieneBichoConId(Integer bichoId){
        return bichos.stream().filter(b-> b.getId() == bichoId).findAny().orElse(null) != null;

    }


    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getNombre(){return nombre;}

    public boolean puedeCapturar() {
        //calculo de bichomon mas 1
        return false;
    }
    public String toString(){
        final String[] b = {" "};
        bichos.forEach(bicho -> b[0] = b[0] + bicho.toString()+ " ");
        return( nombre + " id: "+id +"{"+ b[0] +"}");
    }

    public Bicho capturar() {
        if(haveMaxCantBichos()){
            //romper
        }
        Bicho nuevoBicho = ubicacion.capturar(this);
        nuevoBicho.setFechaCaptura(new Date());
        nuevoBicho.setOwner(this);
        return nuevoBicho;
    }

    private boolean haveMaxCantBichos() {
        //no me encargo, a quien corresponda hagalo
        return false;
    }
}
