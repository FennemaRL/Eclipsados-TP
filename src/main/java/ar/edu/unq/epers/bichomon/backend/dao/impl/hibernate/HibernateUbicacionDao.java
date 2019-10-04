package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.validation.ConstraintViolationException;

public class HibernateUbicacionDao extends HibernateDAO<Ubicacion> implements UbicacionDao {

    public HibernateUbicacionDao() {super(Ubicacion.class);}

    @Override
    public void guardarUbicacion(Ubicacion ubi) {
        Session session = TransactionRunner.getCurrentSession();
        try{
            session.save(ubi); }
        catch (ConstraintViolationException e){
            throw new ErrorGuardar("Existe una Ubicacion con ese nombre");
        }
    }

    @Override
    public Ubicacion recuperarUbicacion(String ubi) {
        Session session = TransactionRunner.getCurrentSession();

        String hql = "from Ubicacion u where u.nombreUbicacion = :ubi";

        Query<Ubicacion> query = session.createQuery(hql,  Ubicacion.class);

        query.setParameter("ubi",ubi);
        query.setMaxResults(1);
        return query.getSingleResult();
    }


    @Override
    public void reset() {
        Session session = TransactionRunner.getCurrentSession();

        session.close();
    }

    @Override
    public void actualizar(Object t) {
        Session session = TransactionRunner.getCurrentSession();
        session.update(t);
    }


}
