package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Energia")
public class Energia extends Condicion{

    public Energia(){
        super();
    }

    public boolean cumpleCondicion(Entrenador entrenador, Bicho bicho){
        // Indica si cumple la condicion para evolucionar
        return this.getPuntos() <= bicho.getEnergia();
    }
}
