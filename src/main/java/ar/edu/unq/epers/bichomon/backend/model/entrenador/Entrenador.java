package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.Exception.EntrenadorException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrenador {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Integer experiencia;
    private Integer nivel;

    public Entrenador(int id, String nombre, Integer exp, Integer lvl){
        this.id= id;
        this.name = nombre;
        this.experiencia= exp;
        this.nivel= lvl;
        bichos = new ArrayList<Bicho>();
    }

    @OneToMany(mappedBy="owner", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichos;

    private Ubicacion ubicacion;


    public Bicho getBichoConID(Integer bichoId) {
        //Bicho bichoEncontrar = bichos.stream().findAny(b-> b.getId() == bicho) ;
        Bicho bichoEncontrar = bichos.stream().filter(b-> b.getId() == bichoId).findAny().orElse(null) ;
        if (bichoEncontrar == null) throw new EntrenadorException(this, bichoId);
        return bichoEncontrar;
    }

    public void agregarBichomon(Bicho unBicho) {
        unBicho.setFechaCaptura(DateTime.now());
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



}
