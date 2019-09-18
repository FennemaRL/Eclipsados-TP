package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class ZonaErronea extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ZonaErronea(String msg) {
        super(msg);
    }
}
