package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public interface EntrenadorDAO {

    void guardar(Entrenador entrenador);

    Entrenador recuperar(String nombreEntrenador);
}
