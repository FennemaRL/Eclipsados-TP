package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

public class EspecieConProv {
    public Especie getEsp() {
        return esp;
    }

    private Especie esp ;

    public int getProv() {
        return prov;
    }

    private int prov;

    public EspecieConProv(Especie esp,int prov){
        this.esp = esp;
        this.prov = prov;
    }
}
