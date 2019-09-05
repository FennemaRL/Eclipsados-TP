package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCEspecieDAO implements EspecieDAO {
    public JDBCEspecieDAO(){

    }

    @Override
    public void guardar(Especie especie) {
        this.executeWithConnection(conn ->{
            PreparedStatement sp = conn.prepareStatement("INSERT INTO specie (id, nombre, peso, altura, tipo_Bicho, cantidad_Bichos) VALUES(?,?,?,?,?,?)");
            sp.setInt(1,especie.getId());
            sp.setString(2, especie.getNombre());
            sp.setInt(3,especie.getPeso());
            sp.setInt(4,especie.getAltura());
            sp.setString(5,especie.getTipo().toString());
            sp.setInt(6,especie.getCantidadBichos());
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
        /*Para implementar*/
    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        return this.executeWithConnection(conn -> {
            PreparedStatement sp = conn.prepareStatement("SELECT id, nombre, peso, altura, tipo_Bicho, cantidad_Bichos FROM specie WHERE  nombre= ?");
            sp.setString(1, nombreEspecie);
            ResultSet resultSet = sp.executeQuery();
            Especie especie = null;
            ArrayList tbl = new ArrayList<TipoBicho>();
            for (int i = 0; i < TipoBicho.values().length; i=i+ 1){
                tbl.add(TipoBicho.values()[i]);
            }
            while(resultSet.next()){
                if(especie != null)
                    throw  new RuntimeException("Existe mas de una especie con el nombre" + nombreEspecie);

                String tipo =resultSet.getString("tipo_Bicho");
                TipoBicho tb= (TipoBicho) tbl.stream().filter(tp-> tp.toString().equals(tipo)).toArray()[0];
                int id= resultSet.getInt("id");
                especie = new Especie (id,nombreEspecie, tb);
                especie.setAltura(resultSet.getInt("Altura"));
                especie.setPeso(resultSet.getInt("Peso"));
                especie.setCantidadBichos(resultSet.getInt("cantidad_Bichos"));
            }
            sp.close();
            return especie;
        });

    }

    @Override
    public List<Especie> recuperarTodos() {
        /*Para implementar*/
        return null;
    }
    public void restart(){
        this.executeWithConnection(conn ->{
            PreparedStatement sp = conn.prepareStatement("DELETE FROM specie WHERE id>=?");
            sp.setInt(1,0);
            sp.execute();

            if(sp.getUpdateCount() < 1){
                throw new RuntimeException("no se encontro ningun objeto");
            }
            sp.close();
            return null;

        });
    }

    private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        Connection connection = this.openConnection("jdbc:mysql://localhost:3306/bichomon_go_jdbc?user=root&password=password&useSSL=false");
        try {
            return bloque.executeWith(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error no esperado", e);
        } finally {
            this.closeConnection(connection);
        }
    }
    private Connection openConnection(String url) {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}

