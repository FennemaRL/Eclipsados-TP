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
    protected int cantidadEntrenadores;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int id;

    private int cantidadDeEntrenadores;

    public Ubicacion(){}
    public Ubicacion(String name) {
        nombreUbicacion = name;

        cantidadDeEntrenadores = 0;

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


    public int getCantidadDeEntrenadores(){ return this.cantidadDeEntrenadores;}

    public void incrementarCantidadDeEntrenadores(){this.cantidadDeEntrenadores++;};

    public void decrementarCantidadDeEntrenadores(){this.cantidadDeEntrenadores--;}

    public void setCantidadDeEntrenadores(int i){
        this.cantidadDeEntrenadores= i;
    };

    public  Bicho getBichoCampeon(){
        throw new ZonaErronea("Esta no es un dojo"+this.nombreUbicacion);
    }

}
