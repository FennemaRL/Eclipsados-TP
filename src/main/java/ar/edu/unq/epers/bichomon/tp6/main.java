package ar.edu.unq.epers.bichomon.tp6;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.EntrenadorService;


public class main {
    public static void main(String[] args) throws Exception {
        EntrenadorService entrenadorService = new EntrenadorService(new HibernateEntrenadorDao());
        BichoService bichoService = new BichoService(entrenadorService);
        Ubicacion islakame = new Dojo("isla kame",new RandomBichomon());
        Especie evo = new Especie("gyarados", TipoBicho.AGUA,100,100,0);
        Especie preEvo = new Especie("magikarp", TipoBicho.AGUA,1,1,0);
        evo.setEspecieRaiz(preEvo);
        Bicho bicho = new Bicho(evo);
        Entrenador krillin = new Entrenador("krillin",islakame);
        krillin.agregarBichomon(bicho);
        entrenadorService.guardar(krillin);

        bichoService.duelo(krillin.getNombre(),bicho.getId());

        for (int i=1 ; i< 10 ; i++){
            Entrenador krillinN = new Entrenador("krillin"+i,islakame);
            entrenadorService.guardar(krillinN);
            Entrenador krillinCopy = entrenadorService.recuperar("krillin"+i);
            bichoService.buscar(krillinCopy.getNombre());

        }
    }
}

