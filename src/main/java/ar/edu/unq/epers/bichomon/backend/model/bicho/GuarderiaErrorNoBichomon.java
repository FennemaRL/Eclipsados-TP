package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class GuarderiaErrorNoBichomon extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GuarderiaErrorNoBichomon(String msg) {
        super(msg);
    }
}