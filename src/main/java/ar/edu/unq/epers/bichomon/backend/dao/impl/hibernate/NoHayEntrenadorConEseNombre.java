package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

public class NoHayEntrenadorConEseNombre extends RuntimeException {
    public NoHayEntrenadorConEseNombre(String s) {
        super(s);
    }
}
