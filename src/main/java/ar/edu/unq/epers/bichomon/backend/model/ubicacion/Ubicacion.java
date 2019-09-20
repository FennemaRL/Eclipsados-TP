package ar.edu.unq.epers.bichomon.backend.model.ubicacion;


import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Ubicacion {

    protected String nombreUbicacion;

    @Id
    @GeneratedValue
    protected int id;

    public Ubicacion(String name) {
        nombreUbicacion = name;
    }

    public abstract Bicho capturar(Entrenador e);

    public void adoptar(Bicho bicho) {
        throw new ZonaErronea("No puedes abandonar tu "+bicho.getEspecie().getNombre()+" en"+this.nombreUbicacion);
    }
    public void retar(Entrenador entrenador, Bicho bichomon){
        throw new ZonaErronea("No puedes combatir aqui en "+this.nombreUbicacion);
    }

    protected String getNombreUbicacion(){
        return nombreUbicacion;
    }
}
