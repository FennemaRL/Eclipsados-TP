package ar.edu.unq.epers.bichomon.backend.service.runner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Supplier;

public class TransactionRunner {
	private static ThreadLocal<Session> CONTEXTO = new ThreadLocal<>();

	public static void runInSession(Runnable bloque) {
		runInSession(()->{
			bloque.run();
			return null;
		});
	}

	
	public static <T> T runInSession(Supplier<T> bloque) {
		if(CONTEXTO.get() != null){
			return bloque.get();
		}
		try {
			comenzarTransaccion();

			//codigo de negocio
			T resultado = bloque.get();
			
			commit();
			return resultado;
		} catch (RuntimeException e) {
			rollback();
			//solamente puedo cerrar la transaccion si fue abierta antes,
			//puede haberse roto el metodo ANTES de abrir una transaccion
			throw e;
		}
	}

	private static void rollback() {
		Session session = CONTEXTO.get();
		if (session != null) {
			session.getTransaction().rollback();
			session.close();
			CONTEXTO.set(null);
		}
	}

	private static void commit() {
		Session session = CONTEXTO.get();
		session.getTransaction().commit();
		session.close();
		CONTEXTO.set(null);
	}

	private static void comenzarTransaccion() {
		Session session = SessionFactoryProvider.getInstance().createSession();
		Transaction tx = session.beginTransaction();
		CONTEXTO.set(session);
	}

	public static Session getCurrentSession() {
		Session session = CONTEXTO.get();
		if (session == null) {
			throw new RuntimeException("No hay ninguna session en el contexto");
		}
		return session;
	}

	
}
