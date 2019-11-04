package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;


import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

import java.util.List;


public class UbicacionNeoDao {
    private Driver driver;
    private UbicacionService su;

    public UbicacionNeoDao(UbicacionService su){
        this.driver = GraphDatabase.driver( "bolt://localhost:11002", AuthTokens.basic( "neo4j", "password" ) );
        this.su=su;
    }                                                                                           //BichomonG                 Viejo1234!
    public void crearNodo(Ubicacion ubicacion) {
        Session session = this.driver.session();
        try {
            String q ="";
            String[] p = ubicacion.getClass().getName().toLowerCase().split("[.]");
            String tipo =p[(p.length)-1];
            if (tipo.equals("pueblo"))
                    q = "Merge (n:Ubicacion:Pueblo {nombre: {elNombre}}) ";
            if (tipo.equals("dojo"))
                    q = "Merge (n:Ubicacion:dojo {nombre: {elNombre}}) ";
            if (tipo.equals("guarderia"))
                    q = "Merge (n:Ubicacion:Guarderia {nombre: {elNombre}}) ";

            session.run(q,Values.parameters("elNombre",ubicacion.getNombre()));
        }

        finally {
            session.close();
        }
    }

    public void crearRelacionPorMedioDe(String tipodetransporte, Ubicacion ubicacionViajaDesde, Ubicacion ubicacionViajaHasta) {
        Session session = this.driver.session();

        try {
            String q ="MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}})" +
                      "MATCH (toUbicacion:Ubicacion{nombre:{eltoUNombre}})  " +
                      "MERGE (fromUbicacion)-[:seViajaA]->(toUbicacion)" ;
            session.run(q,Values.parameters("elfromUNombre",ubicacionViajaDesde.getNombre(),"eltoUNombre",ubicacionViajaHasta.getNombre()));
        }
        finally {
            session.close();
        }
    }
    public List<Ubicacion> seViajaDesde(Ubicacion ubicacion){
        Session session = this.driver.session();

        try {
            String q = "MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}})" +
                    "MATCH (fromUbicacion)-[r:seViajaA]->(toUbicacion)  " +
                    "RETURN toUbicacion";
            StatementResult result =session.run(q, Values.parameters("elfromUNombre", ubicacion.getNombre()));
            return result.list((record -> {
                Value ub = record.get(0);
                String nombre = ub.get("nombre").asString();
                return su.recuperar(nombre);
            }));
        }
        finally {
            session.close();
        }
    }

    public void borrartodo() {
    }
}
