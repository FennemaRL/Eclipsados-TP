package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import javax.persistence.*;


@Entity
@Table(name = "Experiencia")
public class ExperienciaValor {
    @Id
    @GeneratedValue (strategy = javax.persistence.GenerationType.IDENTITY )
    public int id;

    @Column
    private int puntosCombatir;
    private int puntosCapturar;
    private int puntosEvolucion;

    public ExperienciaValor(){}

    public ExperienciaValor(int combatir, int captura, int evolucion){
        puntosCapturar = captura;
        puntosEvolucion = evolucion;
        puntosCombatir = combatir;
    }
    public int getPuntosCombatir() {
        return puntosCombatir;
    }

    public void setPuntosCombatir(int puntosCombatir) {
        this.puntosCombatir = puntosCombatir;
    }

    public int getPuntosCapturar() {
        return puntosCapturar;
    }

    public void setPuntosCapturar(int puntosCapturar) {
        this.puntosCapturar = puntosCapturar;
    }

    public int getPuntosEvolucionar() {
        return puntosEvolucion;
    }

    public void setPuntosEvolucion(int puntosEvolucion) {
        this.puntosEvolucion = puntosEvolucion;
    }

}