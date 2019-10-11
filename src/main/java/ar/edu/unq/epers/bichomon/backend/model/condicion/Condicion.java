package ar.edu.unq.epers.bichomon.backend.model.condicion;


import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Condicion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int id;

    @Column
    public int puntos;


    public Condicion(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public abstract boolean cumpleCondicion(Entrenador entrenador, Bicho bicho);








}
