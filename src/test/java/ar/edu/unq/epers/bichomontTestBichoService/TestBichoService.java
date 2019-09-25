package ar.edu.unq.epers.bichomontTestBichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.NoHayEntrenadorConEseNombre;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.BichoService;
import org.junit.Test;

public class TestBichoService {


    @Test
    public void al_buscar_un_Entrenador_que_no_existe_levanta_una_excepcion(){
        BichoService bs = new BichoService(new HibernateEntrenadorDao(),new HibernateEspecieDao(),new HibernateBichoDao());
        Entrenador entre = new Entrenador();
        Especie esp = new Especie();
        bs.guardarEntrenador(entre);
        bs.guardarEspecie(esp);
    }
}
