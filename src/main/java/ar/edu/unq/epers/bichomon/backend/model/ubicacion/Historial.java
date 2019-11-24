package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
@Entity
public class   Historial {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    private Entrenador entrenador;
    @ManyToOne (fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    private Bicho bicho;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    //@Formula("(Select (coalesce(h.fechaFin, utc_timestamp()) - h.fechaInicio)  from historial h where h.id = id)")
    @Formula("(Select (coalesce(h.fechaFin, NOW()) - h.fechaInicio)  from historial h where h.id = id)")
    private Double diferencia;

    public Historial(){}

    public Historial(Entrenador entrenador, Bicho bicho, Date fecha) {
        this.entrenador = entrenador;
        this.fechaInicio = fecha;
        this.bicho = bicho;
    }

    public void setFechaFin(Date fecha) {this.fechaFin = fecha;
    }
    @Override
    public String toString(){
        return "Historial id :"+id + entrenador.toString() + fechaInicio+" "+ fechaFin +" differencia "+diferencia;
    }
}
