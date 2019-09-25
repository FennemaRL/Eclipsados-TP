package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

public class ErrorRecuperar extends RuntimeException{
    public ErrorRecuperar(String s) {
        super(s);
    }
}
