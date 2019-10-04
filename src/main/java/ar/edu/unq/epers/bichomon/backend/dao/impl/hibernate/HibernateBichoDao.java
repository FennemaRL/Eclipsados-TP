package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class HibernateBichoDao extends HibernateDAO<Bicho> implements BichoDAO {
    public HibernateBichoDao() { super(Bicho.class); }

    @Override
    public void reset() {

    }

    @Override
    public void actualizar(Object t) {

    }

}
