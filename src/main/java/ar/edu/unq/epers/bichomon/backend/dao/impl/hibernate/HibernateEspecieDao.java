package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;


import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public class HibernateEspecieDao extends HibernateDAO<Especie> implements EspecieDAO {
    public HibernateEspecieDao() {
        super(Especie.class);
    }

    @Override
    public void actualizar(Especie especie) {

    }

    @Override
    public List<Especie> recuperarTodos() {
        return null;
    }

    @Override
    public void restart() {

    }
}
