package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class MapaService {

    private EntrenadorService entrenadorService;
    private UbicacionService ubicacionService;

    public MapaService(EntrenadorService entrenadorService1, UbicacionService ubicacionService1) {
        entrenadorService = entrenadorService1;
        ubicacionService = ubicacionService1;
    }

    public void mover(String entrenador, String ubicacion) {
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);
        Ubicacion ubicacion0 = entrenador1.getUbicacion();
        Ubicacion ubicacion1 = ubicacionService.recuperar(ubicacion);

        entrenador1.setUbicacion(ubicacion1);
        //decrementar e incrementar entrenadores en ubicaciones
        //y actualizar ubicacion 0, ubicacion 1 se actualiza x cascade
        entrenadorService.actualizar(entrenador1);
    }
    public Bicho campeonHistorico(String dojo){
        return ubicacionService.campeonHistorico(dojo);
    }
}
