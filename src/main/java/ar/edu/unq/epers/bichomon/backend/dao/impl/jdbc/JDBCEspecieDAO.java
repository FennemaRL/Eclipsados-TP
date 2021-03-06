package ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.sql.*;
import java.util.*;

public class JDBCEspecieDAO implements EspecieDAO {
    public JDBCEspecieDAO(){

    }

    @Override
    public void guardar(Especie especie) {

    }

    @Override
    public void actualizar(Especie especie) {

    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        return null;
    }

    @Override
    public List<Especie> recuperarTodos() {
        return null;
    }

    @Override
    public void restart() {

    }
/*
    @Override
    public void guardar(Especie especie) {
        this.executeWithConnection(conn ->{
            PreparedStatement sp = conn.prepareStatement("INSERT INTO specie (nombre, peso, altura, tipo_Bicho, cantidad_Bichos) VALUES(?,?,?,?,?)");
            sp.setString(1, especie.getNombre());
            sp.setInt(2,especie.getPeso());
            sp.setInt(3,especie.getAltura());
            sp.setString(4,especie.getTipo().toString());
            sp.setInt(5,especie.getCantidadBichos());
            sp.execute();

            if(sp.getUpdateCount() != 1){
                throw new RuntimeException("no se inserto la especie " + especie);
            }
            sp.close();
            return null;
        });

    }

    @Override
    public void actualizar(Especie especie) {
        if( especie.getId() == null)
            throw new JDBCEspecieDAOError("La especie a actualizar no tiene id");
        this.executeWithConnection(conn ->{
            PreparedStatement sp = conn.prepareStatement("UPDATE specie  SET nombre=?, peso=?, altura=?, tipo_Bicho=?, cantidad_Bichos=? WHERE id=? ");

            sp.setString(1, especie.getNombre());
            sp.setInt(2,especie.getPeso());
            sp.setInt(3,especie.getAltura());
            sp.setString(4,especie.getTipo().toString());
            sp.setInt(5,especie.getCantidadBichos());
            sp.setInt(6,(int)especie.getId());//condition where
            sp.executeUpdate();

            if(sp.getUpdateCount() < 1){
                throw new JDBCEspecieDAOError("no se actualizo " + especie);
            }
            sp.close();
            return null;

        });

    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        return this.executeWithConnection(conn -> {
            PreparedStatement sp = conn.prepareStatement("SELECT id, nombre, peso, altura, tipo_Bicho, cantidad_Bichos FROM specie WHERE  nombre= ?");
            sp.setString(1, nombreEspecie);
            ResultSet resultSet = sp.executeQuery();
            Especie especie = null;
            while(resultSet.next()){
                if(especie != null)
                    throw  new RuntimeException("Existe mas de una especie con el nombre" + nombreEspecie);

                String tipo =resultSet.getString("tipo_Bicho");
                TipoBicho tb=TipoBicho.valueOf(tipo);
                int id= resultSet.getInt("id");
                especie = new Especie (id,nombreEspecie, tb);
                especie.setAltura(resultSet.getInt("Altura"));
                especie.setPeso(resultSet.getInt("Peso"));
                especie.setCantidadBichos(resultSet.getInt("cantidad_Bichos"));
            }
            if(especie == null) {
                throw new JDBCEspecieDAOError("no se encontro una especie con ese nombre ");
            }
            sp.close();
            return especie;
        });

    }

    @Override
    public List<Especie> recuperarTodos() {
        List<Especie> res = new ArrayList<>();

        return this.executeWithConnection(conn -> {
            PreparedStatement sp = conn.prepareStatement("SELECT * FROM specie ORDER BY nombre ASC");
            ResultSet resultSet = sp.executeQuery();

            while(resultSet.next()){
                TipoBicho tp = TipoBicho.valueOf(resultSet.getString("tipo_Bicho"));
                res.add(new Especie(resultSet.getInt("id"),resultSet.getString("nombre"),resultSet.getInt("peso"),resultSet.getInt("altura"),tp,resultSet.getInt("cant_Bichos")));
            }
        sp.close();
        return res;
        });
    }

    public void restart(){
        this.executeWithConnection(conn ->{
            PreparedStatement sp = conn.prepareStatement("TRUNCATE TABLE bichomon_go_jdbc.specie");
            sp.execute();
            sp.close();
            return null;
        });
    }

    private <T> T executeWithConnection(ConnectionBlock<T> bloque) {

        Connection connection = this.openConnection("jdbc:mysql://localhost:3306/bichomon_go_jdbc?user=root&password=Viejo1234!&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");

        try {
            return bloque.executeWith(connection);
        } catch (SQLException e) {
            throw new JDBCEspecieDAOError("Error no esperado", e);
        } finally {
            this.closeConnection(connection);
        }
    }
    private Connection openConnection(String url) {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new JDBCEspecieDAOError("No se puede establecer una conexion", e);
        }
    }
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }*/
}

