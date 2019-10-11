package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

public class NoHayUbicacionConEseNombre extends RuntimeException {
    public NoHayUbicacionConEseNombre(String s) {
        super(s);
    }
}