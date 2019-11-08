package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;

@Entity
public class EspecieConProv {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    public Especie getEsp() {
        return esp;
    }
    @ManyToOne(cascade = CascadeType.ALL )
    private Especie esp ;
    public EspecieConProv(){

    }

    public int getProv() {
        return prov;
    }

    private int prov;

    public EspecieConProv(Especie esp,int prov){
        this.esp = esp;
        this.prov = prov;
    }
}
