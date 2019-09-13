package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;

/**
 * Esta clase es un singleton, el cual sera utilizado por equipo de frontend
 * para hacerse con implementaciones a los servicios.
 * 
 * @author Steve Frontend
 * 
 * TODO: Gente de backend, una vez que tengan las implementaciones de sus
 * servicios propiamente realizadas apunten a ellas en los metodos provistos
 * debajo. Gracias!
 */
public class ServiceFactory {
	
	/**
	 * @return un objeto que implementa {@link EspecieService}
	 */
	public EspecieService getEspecieService() {return new EspecieServiceImpl(new JDBCEspecieDAO());
		//return new EspecieServiceImpl(new EspecieDAOMock());
	}
	
	/**
	 * @return un objeto que implementa {@link DataService}
	 */
	public DataService getDataService() {
		throw new RuntimeException("Todavia no se ha implementado este metodo");
	}

}
