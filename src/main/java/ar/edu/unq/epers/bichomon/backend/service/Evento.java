package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

public class Evento {
    private  String accion;
    private  Date date;
    private  String nombreub;

    public String getId() {
        return id;
    }

    @MongoId
    @MongoObjectId
    private String id;
    private String entrenador ;
    private String extra;

    public Evento(){}
    public Evento(Entrenador ent){
        this.entrenador = ent.getNombre();
    }

    public Evento(Entrenador entrenador, String accion, Date date, String nombre) {
        this.entrenador= entrenador.getNombre();
        this.accion = accion;
        this.date = date;
        this.nombreub = nombre;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass()!= this.getClass())
            return false;
        else return id.equals (((Evento) o).getId());

    }

    public void setExtra(String nombre) {
        this.extra =nombre;
    }

    public String getnombreub() {
        return nombreub;
    }

    @Override
    public String toString() {
        return "id: "+id+" accion: "+accion+" lugar:"+nombreub +"\n";
    }

    public String getAccion() {
        return accion;
    }
}
