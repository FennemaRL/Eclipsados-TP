package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;


import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

public class HibernateEspecieDao extends HibernateDAO<Especie> implements EspecieDAO {
    public HibernateEspecieDao() {
        super(Especie.class);
    }

    @Override
    public void actualizar(Especie especie) {
        Session session = TransactionRunner.getCurrentSession();
        try{
            session.update(especie); }
        catch (ConstraintViolationException e){
            throw new ErrorRecuperar("No existe una Especie con ese nombre");
        }
    }

    @Override
    public List<Especie> recuperarTodos() {
        return null;
    }

    @Override
    public void restart() {

    }
    @Override
    public Especie recuperar(String especienombre){
        Session session = TransactionRunner.getCurrentSession();

        String hql = "from Especie e where e.nombre =:especienombre";

        Query<Especie> query = session.createQuery(hql,  Especie.class);

        query.setParameter("especienombre",especienombre);
        query.setMaxResults(1);
        Especie res=null ;
        try{
            res=query.getSingleResult();
        }
        catch (NoResultException nre){}
        if(res == null)
            throw new ErrorRecuperar("no hay Especie con el nombre" +especienombre);
        return res;
    }

    @Override
    public void reset() {

    }

    public void guardar(Especie item) {
        Session session = TransactionRunner.getCurrentSession();
        try{
        session.save(item); }
        catch (ConstraintViolationException e){
            throw new ErrorGuardar("Existe una Especie con ese nombre");
        }
    }
}
