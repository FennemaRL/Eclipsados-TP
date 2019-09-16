package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class BichoService {

    private HibernateDAO entrenadorDAO;

    public BichoService(HibernateDAO entrenadorDAO){

        this.entrenadorDAO = entrenadorDAO;
    }

    public Bicho buscar(String entrenador){
        return null;}

    public boolean puedeEvolucionar(String entrenador, Integer bicho){
        Entrenador entrenador1 = (Entrenador) entrenadorDAO.recuperar(entrenador);
        Bicho elBicho = entrenador1.getBichoConID(bicho);
        return (elBicho.puedeEvolucionar());
    }
}
