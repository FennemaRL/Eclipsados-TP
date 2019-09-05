package ar.edu.unq.epers.bichomon.backend.dao.impl;

import java.sql.SQLException;

public class JDBCEspecieDAOError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JDBCEspecieDAOError(String msg) {
        super(msg);
    }

    public JDBCEspecieDAOError(String s, SQLException e) {
        super(s,e);
    }
}