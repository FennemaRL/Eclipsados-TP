package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

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

    public Bicho campeonHistorico(String dojo){
        Session session = TransactionRunner.getCurrentSession();
        Date date = new Date();


        Query<Bicho> bichoq = session.createSQLQuery("select bicho.id, nombre, especie_id, energiaDeCombate, fechaCaptura, victorias, owner_id " +
                "from historial inner join ubicacion_historial on (historial.id = historial_id) inner join ubicacion inner join bicho on (historial.bicho_id = bicho.id) " +
                "Where ubicacion.nombreUbicacion = :dojo "+
                "ORDER BY TIMEDIFF(COALESCE(historial.fechaFin,:dat3 ), historial.fechaInicio) desc limit 1;").addEntity(Bicho.class);
        bichoq.setParameter("dojo",dojo);
        bichoq.setParameter("dat3",date);
        List<Bicho> bs=bichoq.getResultList();
        return (bs.size() == 0)? null : bs.get(0);

    }

    public List<Entrenador> campeones() {
        Session session = TransactionRunner.getCurrentSession();
        Date date = new Date();
        Query<Entrenador> entrenadorq = session.createSQLQuery("select entrenador.* " +
                "from historial inner join ubicacion_historial on (historial.id = historial_id)inner join ubicacion on(ubicacion.id = dojo_id) inner join entrenador on (historial.entrenador_id = entrenador.id) " +
                " group by entrenador.id " +
                " ORDER BY TIMEDIFF(COALESCE(historial.fechaFin,:dat3 ), historial.fechaInicio) desc limit 10;").addEntity(Entrenador.class);
        entrenadorq.setParameter("dat3",date);
        return entrenadorq.getResultList();
    }

    public Especie especieLider() {
        Session session = TransactionRunner.getCurrentSession();
        String hql = "Select especie from Historial as historial join historial.bicho as bicho join bicho.especie as especie" +
                " group by especie.id " +
                " order by count(especie.id) desc";
        Query<Especie> especieQ =session.createQuery(hql,Especie.class);
        especieQ.setMaxResults(1);

        return especieQ.uniqueResult();

    }
}
