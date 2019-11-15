package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;


import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import ar.edu.unq.epers.bichomon.backend.service.Transporte;
import ar.edu.unq.epers.bichomon.backend.service.UbicacionService;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;


public class UbicacionNeoDao {
    private Driver driver;
    private UbicacionService su;

    public UbicacionNeoDao(UbicacionService su){
        this.driver = GraphDatabase.driver( "bolt://localhost:11002", AuthTokens.basic( "neo4j", "password" ) );
        this.su=su;
    }

    public boolean existeUbicacion(Ubicacion unaUbicacion){
        Session session = this.driver.session();

        try {
            String q = "MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}}) "+
                    "return fromUbicacion";
            StatementResult result =session.run(q, Values.parameters("elfromUNombre", unaUbicacion.getNombre()));
            return (result.list().get(0).get("nombre").asString() == unaUbicacion.getNombre());
        }

        finally {
            session.close();
        }

    }
    public void crearUbicacion (Ubicacion unaUbicacion){
        if (!existeUbicacion(unaUbicacion)){
            crearNodo(unaUbicacion);
        }
        else{
            throw new ZonaErronea("ya existe ubicacion con ese nombre");
        }
    }


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

    public void crearRelacionDeUbiAUbi(Transporte medioDetransporte, Ubicacion ubicacionViajaDesde, Ubicacion ubicacionViajaHasta) {
        Session session = this.driver.session();
        try {
            String q ="MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}})" +
                      "MATCH (toUbicacion:Ubicacion{nombre:{eltoUNombre}})  " +
                      "MERGE (fromUbicacion)-[r:"+medioDetransporte.name().toLowerCase()+ "]->(toUbicacion)" +
                    "  SET r.tipo={eltipo}" ;
            session.run(q,Values.parameters("elfromUNombre",ubicacionViajaDesde.getNombre(),"eltoUNombre",ubicacionViajaHasta.getNombre(),"eltipo",medioDetransporte.name().toLowerCase()));
        }
        finally {
            session.close();
        }
    }
    public List<Ubicacion> conectadosp2p(Ubicacion ubicacion, Transporte medioDetransporte){
        Session session = this.driver.session();

        try {
            String q = "MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}}) " +
                    "MATCH (fromUbicacion)-[:"+medioDetransporte.name().toLowerCase()+"]->(toUbicacion)  " +
                    "RETURN toUbicacion ";
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

    public void borrarTodo() {
        Session session = this.driver.session();

        try {
            String q ="MATCH (u:Ubicacion)-[relacion]-(u2:Ubicacion)" +
                      " DELETE u,u2,relacion";
            session.run(q);
        }
        finally {
            session.close();
        }
    }

    public List<String> estaConectadop2p(Ubicacion ubicacionfrom, Ubicacion ubicacionTo) {
        Session session = this.driver.session();

        try {
            String q = "MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}}) " +
                    "MATCH (toUbicacion:Ubicacion{nombre:{eltoUNombre}}) " +
                    "MATCH (fromUbicacion)-[r]->(toUbicacion)  " +
                    "RETURN r";
            StatementResult result =session.run(q, Values.parameters("elfromUNombre", ubicacionfrom.getNombre().toLowerCase(),"eltoUNombre",ubicacionTo.getNombre().toLowerCase()));
            return result.list((record -> {
                Value ub = record.get(0);
                return ub.get("tipo").asString();
            }));
        }
        finally {
            session.close();
        }
    }
    public List<List<String>> estaConectadoN(Ubicacion ubicacionfrom, Ubicacion ubicacionTo) {
        Session session = this.driver.session();
        boolean rutanoencontrada ;
        try {
            StatementResult result;
            int saltos  = 1;
            do  {
                String q ="MATCH (fromUbicacion:Ubicacion{nombre:{elfromUNombre}}) " +
                        "MATCH (toUbicacion:Ubicacion{nombre:{eltoUNombre}}) " +
                        "MATCH (fromUbicacion)-[r:maritimo|aereo|terrestre*"+saltos+"]->(toUbicacion)  " +
                        "RETURN r";
                result = session.run(q, Values.parameters("elfromUNombre", ubicacionfrom.getNombre().toLowerCase(), "eltoUNombre", ubicacionTo.getNombre().toLowerCase()));
                rutanoencontrada =  ! result.hasNext();
                saltos ++;
            }
            while ( rutanoencontrada && saltos <= 6);
            List<List<String>> ret = new ArrayList<>();
            while(result.hasNext()){
                Value ruta = result.next().get(0);
                List<String> parte = new ArrayList<>();
                for(int i =0 ; i< ruta.size(); i++){
                    String mediot = ruta.get(i).get("tipo").asString();
                    parte.add(mediot);
                }
                ret.add(parte);
                System.out.println(parte);
            }
            return ret;
        }
        finally {
            session.close();
        }


    }
}
