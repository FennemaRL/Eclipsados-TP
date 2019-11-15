package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionNeoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;

public class MapaService {

    private EntrenadorService entrenadorService;
    private UbicacionService ubicacionService;
    private UbicacionNeoDao map;


    public MapaService(EntrenadorService entrenadorService1, UbicacionService ubicacionService1, UbicacionNeoDao neo) {
        entrenadorService = entrenadorService1;
        ubicacionService = ubicacionService1;
        map = neo;
    }

    public void mover(String entrenador, String ubicacion) {//terminar
    runInSession(()->{
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);
        Ubicacion ubicacion0 = entrenador1.getUbicacion();
        Ubicacion ubicacion1 = ubicacionService.recuperar(ubicacion);
        List<String> rutas = map.estaConectadop2p(ubicacion0,ubicacion1);
        if(0 < rutas.size()){
            entrenador1.pagar(this.rutaLowCost(rutas));
            entrenador1.setUbicacion(ubicacion1); //set ubicacion incrementa y decrementa entrenadores en ubicaciones
            ubicacionService.actualizar(ubicacion0); //ubicacion 1 ses actualiza por cascade
            entrenadorService.actualizar(entrenador1);
        }
        else
            throw new UbicacionMuyLejana("ubicacionMuyLejana");
    });
    }

    private int rutaLowCost(List<String> rutas) {
        if(rutas.contains("terrestre")){
            return 5;
        }
        if(rutas.contains("maritimo")){
            return 10;
        }
        if (rutas.contains("aereo")){
            return 15;
        }
        else throw new BichomonError("no deberia pasar por aca mover se a segura de eso");
    }

    public Bicho campeonHistorico(String dojo){
        return ubicacionService.campeonHistorico(dojo);
    }


    public int cantidadEntrenadores(String unaUbicacion) {
        Ubicacion ubicacion = ubicacionService.recuperar(unaUbicacion);
        return(ubicacion.getCantidadDeEntrenadores());
    }


    public Bicho campeon(String ubi) {
        return ubicacionService.recuperar(ubi).getBichoCampeon();
    }

    public void moverMasCorto(String entrenador, String ubicacion) {
        runInSession(()->{
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);
        Ubicacion ubicacion0 = entrenador1.getUbicacion();
        Ubicacion ubicacion1 = ubicacionService.recuperar(ubicacion);
        List<List<String>> rutas = map.estaConectadoN(ubicacion0,ubicacion1);
        if(0 < rutas.size()){
            entrenador1.pagar(this.calcularMasBarata(rutas));
            entrenador1.setUbicacion(ubicacion1); //set ubicacion incrementa y decrementa entrenadores en ubicaciones
            ubicacionService.actualizar(ubicacion0); //ubicacion 1 ses actualiza por cascade
            entrenadorService.actualizar(entrenador1);
        }
        else
            throw new UbicacionMuyLejana("Esta  ubicacion no se encuentra contectada con ninguna otra");
        });
    }

    private int calcularMasBarata(List<List<String>> rutas) {
        AtomicInteger cr = new AtomicInteger(costoDelaRuta(rutas.get(0)));
        rutas.forEach(r->{
            int crp =this.costoDelaRuta(r);
            if(cr.get() >= crp)
                  cr.set(crp);
        });
        System.out.println(cr.get());
        return cr.get();
    }

    private int costoDelaRuta(List<String> ruta) {
        int res = ruta.stream().mapToInt(this::costoDeTransporte).sum();
        return res;
    }

    private Integer costoDeTransporte(String t) {
        switch (t){
            case "terrestre" : return 5;
            case "aereo" : return 15;
            case "maritimo" : return 10;
            default:  throw new BichomonError("no existe ese metodo el metodo de transporte :"+t+"\n");
        }
    }

    public void crearUbicacion(Ubicacion ubicacion) {
        this.ubicacionService.guardar(ubicacion);
        this.map.crearUbicacion(ubicacion);
    }

    public Ubicacion recuperarUbicacion(String ubicacion) {
        return (ubicacionService.recuperar(ubicacion));
    }


}

