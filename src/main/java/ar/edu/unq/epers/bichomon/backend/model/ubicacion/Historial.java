package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.Date;
@Entity
public class   Historial {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    private Entrenador entrenador;
    @ManyToOne (fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    private Bicho bicho;
    private Date fechaInicio;
    private Date fechaFin;

    public Historial(){}

    public Historial(Entrenador entrenador, Bicho bicho, Date fecha) {
        this.entrenador = entrenador;
        this.fechaInicio = fecha;
        this.bicho = bicho;
    }

    public void setFechaFin(Date fecha) {this.fechaFin = fecha;
    }
}
