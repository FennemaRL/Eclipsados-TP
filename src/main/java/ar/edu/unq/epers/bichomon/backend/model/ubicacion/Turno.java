package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class Turno {
    Bicho bicho;
    int daño;
    public Turno(Bicho bRetador, int dañoact) {
        this.bicho = bRetador;
        this.daño = dañoact;
    }
    public Bicho getBicho(){
        return this.bicho;
    }
    public Integer getDaño(){
        return this.daño;
    }
}