package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;


import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;


public class HibernateEntrenadorDao extends HibernateDAO<Entrenador> implements EntrenadorDAO {


        public HibernateEntrenadorDao() {super(Entrenador.class);}

        public Entrenador recuperar (String entrenador){
            Session session = TransactionRunner.getCurrentSession();

            String hql = "from Entrenador e where e.nombre =:entrenadornombre";

            Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);

            query.setParameter("entrenadornombre",entrenador);
            query.setMaxResults(1);
            Entrenador res=null ;
            try {
                res = query.getSingleResult();
            }
            catch (NoResultException no){}
            return res;
        }

    @Override
    public void reset() {
        Session session = TransactionRunner.getCurrentSession();

        session.close();
        }

    @Override
    public void actualizar(Entrenador entrenador) {
        Session session = TransactionRunner.getCurrentSession();
        session.update(entrenador);
    }


}


