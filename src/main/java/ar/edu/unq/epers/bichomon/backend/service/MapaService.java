package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class MapaService {

    private EntrenadorService entrenadorService;
    private UbicacionService ubicacionService;

    public MapaService(EntrenadorService entrenadorService1,UbicacionService unaUbicacionService) {
        entrenadorService = entrenadorService1;
        ubicacionService = unaUbicacionService;
    }

    public void mover(String entrenador, String ubicacion) {
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);

    }

    public int cantidadEntrenadores(String unaUbicacion) {
        Ubicacion ubicacion = ubicacionService.recuperar(unaUbicacion);
        return(ubicacion.getCantidadEntrenadores());
    }


}
