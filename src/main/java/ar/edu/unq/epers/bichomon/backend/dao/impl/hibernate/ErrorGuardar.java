package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

public class ErrorGuardar extends RuntimeException {
    public ErrorGuardar(String s) {
        super(s);
    }
}
