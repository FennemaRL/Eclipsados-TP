package ar.edu.unq.epers.bichomon.backend.model.ubicacion;


import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Ubicacion {
    @Column (unique = true)
    protected String nombreUbicacion;

    @Id
    @GeneratedValue
    protected int id;
    public Ubicacion(){}
    public Ubicacion(String name) {
        nombreUbicacion = name;
    }

    public abstract Bicho capturar(Entrenador e);

    public void adoptar(Bicho bicho) {
        throw new ZonaErronea("No puedes abandonar tu "+bicho.getEspecie().getNombre()+" en"+this.nombreUbicacion);
    }
    public ResultadoCombate retar(Entrenador entrenador, Bicho bichomon){
        throw new ZonaErronea("No puedes combatir aqui en "+this.nombreUbicacion);
    }

    public String getNombreUbicacion(){
        return nombreUbicacion;
    }

    public abstract String getEntrenadorName();

    public abstract String getBichomonName();

    public Entrenador getCampeon(){
        throw new ZonaErronea("Esta no es un dojo"+this.nombreUbicacion);
    }

    public String getNombre(){return this.nombreUbicacion;}

    public int getid() {
        return id;
    }
}
