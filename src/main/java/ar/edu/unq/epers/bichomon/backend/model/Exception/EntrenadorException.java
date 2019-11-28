package ar.edu.unq.epers.bichomon.backend.model.Exception;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class EntrenadorException extends  RuntimeException{

    private Entrenador entrenador;
    private long iDBichomon;


    public EntrenadorException (Entrenador entrenador, long bichomonID ){
        this.entrenador = entrenador;
        iDBichomon = bichomonID;
    }

    @Override
    public String getMessage() {
        return "El entrenador [" + this.entrenador + "] no posee al bichomon con ID: [" + this.iDBichomon + "] ";
    }


}
