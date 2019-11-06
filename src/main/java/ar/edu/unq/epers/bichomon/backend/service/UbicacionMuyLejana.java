package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;

public class UbicacionMuyLejana extends BichomonError {
    public UbicacionMuyLejana(String ubicacionMuyLejana) {
        super(ubicacionMuyLejana);
    }
}
