package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDAO {

    public void guardar(Bicho bicho);

    public Bicho recuperar(Integer bichoId);
}
