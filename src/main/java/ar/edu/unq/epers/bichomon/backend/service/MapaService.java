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

        entrenador1.setUbicacion(ubicacion1); //set ubicacion incrementa y decrementa entrenadores en ubicaciones

        ubicacionService.actualizar(ubicacion0); //ubicacion 1 ses actualiza por cascade
        entrenadorService.actualizar(entrenador1);
    }

    public Bicho campeonHistorico(String dojo){
        return ubicacionService.campeonHistorico(dojo);
    }


    public int cantidadEntrenadores(String unaUbicacion) {
        Ubicacion ubicacion = ubicacionService.recuperar(unaUbicacion);
        return(ubicacion.getCantidadDeEntrenadores());
    }


    public Bicho campeon(Ubicacion ubi) {
        return ubicacionService.recuperarCampeon(ubi);
    }

}
