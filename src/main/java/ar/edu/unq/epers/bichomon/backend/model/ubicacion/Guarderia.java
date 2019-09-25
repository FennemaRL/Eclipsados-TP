package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Guarderia extends Ubicacion{
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichos ;
    public Guarderia (){}
    public Guarderia(String name) {
        super(name);
        bichos = new ArrayList<>();
    }

    @Override
    public Bicho capturar(Entrenador entre) {
        Bicho e;
        if(0 <bichos.size()){
            e = bichos.get(0);
            bichos.remove(0);}
        else
            throw new GuarderiaErrorNoBichomon("la guarderia "+this.getNombreUbicacion()+" no posee bichomones");
        return e;
    }
    @Override
    public void adoptar(Bicho bichoadoptado) {
    bichos.add(bichoadoptado);
    }
}
