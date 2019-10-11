package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BichomonError;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;

import javax.persistence.EntityNotFoundException;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;
//Modificar daos, no existe entrenador, no exisste ubicacion, no existe bicho
public class BichoService {

    private EntrenadorService entrenadorService;

    public BichoService(EntrenadorService entrenadorServis) {
        this.entrenadorService = entrenadorServis;
    }

    public Bicho buscar(String entrenador){

        Bicho bicho = null;
        Entrenador  entre = entrenadorService.recuperar(entrenador);
        bicho = entre.capturar();//romper si tengo mas de los que puedo
        entrenadorService.actualizar(entre);
       return bicho;
    }


    public boolean puedeEvolucionar(String entrenador, Integer bicho){
        Entrenador entrenador1 =  entrenadorService.recuperar(entrenador);
        Bicho elBicho = entrenador1.getBichoConID(bicho);
        return (elBicho.puedeEvolucionar());
    }

    public Bicho evolucionar(String entrenador, int bicho) {
        return run(() -> {
            Entrenador entre = entrenadorService.recuperar(entrenador);
            Bicho bichoo= entre.getBichoConID(bicho);
            bichoo.evolucionar();
            entre.aumentarExpPorEvolucionar();
            entrenadorService.actualizar(entre);
            return bichoo;
        }
        );
    }

    public void abandonarBicho(String entrenador, Integer bicho){
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);
        entrenador1.abandonarBicho(bicho);
        entrenadorService.actualizar(entrenador1);

    }

    public ResultadoCombate duelo(String entrenador, int bichoid){
        Entrenador entrenador1 = entrenadorService.recuperar(entrenador);
        ResultadoCombate rc = entrenador1.duelear(bichoid);
        entrenadorService.actualizar(entrenador1);
        return rc;
    }

}
