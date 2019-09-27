package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.Date;

public class Historial {
    private Entrenador entrenador;
    private Bicho bicho;
    private Date fechaInicio;
    private Date fechaFin;

    public Historial(Entrenador entrenador, Bicho bicho, Date fecha) {
        this.entrenador = entrenador;
        this.fechaInicio = fecha;
        this.bicho = bicho;
    }

    public void setFechaFin(Date fecha) {this.fechaFin = fecha;
    }
}
