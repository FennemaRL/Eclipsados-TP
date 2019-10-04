package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class MapaService {

    private EntrenadorService entrenadorService;
    private UbicacionService ubicacionService;

    public MapaService(EntrenadorService entrenadorService1) {
        entrenadorService = entrenadorService1;
    }

    public void mover(String entrenador, String ubicacion) {
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);

    }
}
