package ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionBlock<T> {

    T executeWith(Connection conn) throws SQLException;

}