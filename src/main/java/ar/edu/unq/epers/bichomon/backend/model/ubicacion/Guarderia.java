package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Guarderia extends Ubicacion{

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bicho> bichos ;

    public Guarderia (){}
    public Guarderia(String name) {
        super(name);
        bichos = new ArrayList<>();
    }

    @Override
    public Bicho capturar(Entrenador entre) {
        List<Bicho> bichosFiltrados ;

        bichosFiltrados= bichos.stream().filter(bicho -> bicho.getOwner() == null || ! bicho.getOwner().getNombre().equals(entre.getNombre())).collect(Collectors.toList());;

        if(bichosFiltrados.size() == 0)
            throw new GuarderiaErrorNoBichomon("la guarderia " + this.getNombreUbicacion() + " no posee bichomones");

        Bicho bichoEntrega = bichosFiltrados.get(0);
        bichosFiltrados.remove(0);
        return bichoEntrega;
    }
    @Override
    public void adoptar(Bicho bichoadoptado) {
    bichos.add(bichoadoptado);
    }

    @Override
    public String getEntrenadorName() {
        return null;
    }

    @Override
    public String getBichomonName() {
        return null;
    }


    public List<Bicho> getBichos(){
        return this.bichos;
    }
}
