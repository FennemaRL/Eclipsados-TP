package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.runInSession;
//Modificar daos, no existe entrenador, no exisste ubicacion, no existe bicho
public class BichoService implements Observado{

    private EntrenadorService entrenadorService;
    private List<Oyente> oyentes;

    public BichoService(EntrenadorService entrenadorServis) {
        this.entrenadorService = entrenadorServis;
        oyentes = new ArrayList<>();
    }

    public Bicho buscar(String entrenador){ //listo capturar
        final Entrenador[] entre = {null};
        Bicho bichoretorno = TransactionRunner.runInSession(()->{Bicho bicho = null;
            entre[0] = entrenadorService.recuperar(entrenador);
            bicho = entre[0].capturar();//romper si tengo mas de los que puedo
            entrenadorService.actualizar(entre[0]);
            return bicho;});
        Evento ev =new Evento(entre[0],"captura bicho",new Date(),entre[0].getUbicacion().getNombre());
        ev.setExtra(bichoretorno.getEspecie().getNombre());
        oyentes.forEach(oyente -> oyente.nuevoEvento(ev));
        return bichoretorno;
    }


    public boolean puedeEvolucionar(String entrenador, Integer bicho){
        Entrenador entrenador1 =  entrenadorService.recuperar(entrenador);
        Bicho elBicho = entrenador1.getBichoConID(bicho);
        return (elBicho.puedeEvolucionar());
    }

    public Bicho evolucionar(String entrenador, int bicho) {
        return TransactionRunner.runInSession(() -> {
            Entrenador entre = entrenadorService.recuperar(entrenador);
            Bicho bichoo= entre.getBichoConID(bicho);
            bichoo.evolucionar();
            entre.aumentarExpPorEvolucionar();
            entrenadorService.actualizar(entre);
            return bichoo;
        }
        );
    }

    public void abandonarBicho(String entrenador, Integer bicho){ // abandonar listo
        final Evento[] ne = {null};
        runInSession(()->{
            Entrenador entrenador1 = entrenadorService.recuperar(entrenador);
            entrenador1.abandonarBicho(bicho);
            ne[0] = new Evento(entrenador1,"abandona bicho en guarderia",new Date(), entrenador1.getUbicacion().getNombre());
            entrenadorService.actualizar(entrenador1);
        });
        oyentes.forEach(oyente -> oyente.nuevoEvento(ne[0]));

    }

    public ResultadoCombate duelo(String entrenador, int bichoid){ // duelo listo
        final Entrenador[] entrenador1 = {null};
        ResultadoCombate rc1 = TransactionRunner.runInSession(()->{
            entrenador1[0] = entrenadorService.recuperar(entrenador);
        ResultadoCombate rc = entrenador1[0].duelear(bichoid);
        entrenadorService.actualizar(entrenador1[0]);
        return rc;});
        if(entrenador1[0].tieneBichoConId(rc1.getGanador().getId())){
            Evento ne = new Evento(entrenador1[0], "el guachin se corona", new Date(), entrenador1[0].getUbicacion().getNombre());
            oyentes.forEach(oyente -> oyente.nuevoEvento(ne));
        }

        return rc1;
    }

    @Override
    public void agregarOyente(Oyente el) {
        oyentes.add(el);
    }
}
