package ar.edu.unq.epers.bichomon.backend.model.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.jongo.marshall.jackson.oid.MongoId;

public class Evento {
    @MongoId
    private int id ;

    private String entrenador ;
    public Evento(){}
    public Evento(Entrenador ent){
        this.entrenador = ent.getNombre();
    }
    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass()!= this.getClass())
            return false;
        else return id == ((Evento) o).getid();

    }

    public int getid() {
        return id;
    }
}
