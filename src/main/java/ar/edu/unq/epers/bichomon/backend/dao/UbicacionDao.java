package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public interface UbicacionDao {

    void guardarUbicacion(Ubicacion ubi);

    Ubicacion recuperarUbicacion(String ubi);
}
