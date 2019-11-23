package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;


import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public abstract class HibernateDAO<T> {

    private Class<T> entityType;

    public HibernateDAO(Class<T> entityType){
        this.entityType = entityType;
    }

    public void guardar(T item) {
        Session session = TransactionRunner.getCurrentSession();
        session.save(item);
    }

    public T recuperar(String id) {
        Session session = TransactionRunner.getCurrentSession();
        return session.get(entityType, id);
    }
    public T recuperar(Integer id){
        Session session = TransactionRunner.getCurrentSession();
        return session.get(entityType, id);
    }

    abstract public void reset();

    public abstract void actualizar(T item);

    public void clear1(){
        Session session = TransactionRunner.getCurrentSession();
        List<String> nombreDeTablas = session.createNativeQuery("show tables").getResultList();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        nombreDeTablas.forEach(tabla->{
            session.createNativeQuery("truncate table " + tabla).executeUpdate();
        });
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();
    }
    public void clear(){
        Session session = TransactionRunner.getCurrentSession();
        List<String> nombreDeTablas = session.createNativeQuery("show tables").getResultList();
        nombreDeTablas.forEach(tabla->{
            session.createNativeQuery("truncate table " + tabla +" cascade").executeUpdate();
        });
    }

    public  List<Entrenador> lideres(){
        Session session = TransactionRunner.getCurrentSession();
        String hql ="select e from Entrenador e  inner join e.bichos as b " +
                " group by e.id " +
                " order by sum(b.energiaDeCombate) desc";
        Query query = session.createQuery(hql,  Entrenador.class);
        query.setMaxResults(10);

        return query.getResultList();
    }

    public List<Especie> impopulares(){
        Session session = TransactionRunner.getCurrentSession();
        String hql ="select e from Guarderia g  inner join g.bichos as b inner join b.especie as e " +
                " where b.especie = e.id " +
                " group by b.especie " +
                " order by count(b.especie) desc";
        Query query = session.createQuery(hql,  Especie.class);
        query.setMaxResults(10);

        return query.getResultList();
    }

    public List<Especie> populares(){
        Session session = TransactionRunner.getCurrentSession();
        String hql ="select esp from Entrenador e  inner join e.bichos as b inner join b.especie esp" +
                " group by b.especie " +
                " order by count(b.id) desc";
        Query query = session.createQuery(hql,  Especie.class);
        query.setMaxResults(10);

        return query.getResultList();
    }
}
