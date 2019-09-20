package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class BichomonError extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BichomonError(String msg) {
        super(msg);
    }
}
