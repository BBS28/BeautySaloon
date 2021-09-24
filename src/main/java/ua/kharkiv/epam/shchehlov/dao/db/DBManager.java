package ua.kharkiv.epam.shchehlov.dao.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static final Logger log = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DataSource ds;

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            ds = (DataSource) envContext.lookup("jdbc/BSdb");
        } catch (NamingException ex) {
            log.error("Cannot obtain DataSource", ex);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException ex) {
            log.error("Cannot obtain Connection", ex);
        }
        return connection;
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                log.error("Cannot close Connection", ex);
            }
        }
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                log.error("Cannot close Statement", ex);
            }
        }
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                log.error("Cannot close resultSet", ex);
            }
        }
    }

    public void close(Connection con, Statement stmt) {
        close(stmt);
        close(con);
    }

    public void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error("Cannot rollback transaction", ex);
            }
        }
    }

}
