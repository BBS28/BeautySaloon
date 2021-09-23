package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.ClientDao;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private static final Logger log = Logger.getLogger(ClientDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_CLIENTS = "SELECT * FROM account WHERE role = 'CLIENT'";
    private static final String FIND_CLIENT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String FIND_CLIENT_BY_LOGIN = "SELECT * FROM account WHERE surname = ? AND role= 'CLIENT'";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM account WHERE id = ?";
    private static final String INSERT_NEW_CLIENT =
            "INSERT INTO account " +
                    "(login, password, role, name, surname, email) " +
                    "VALUES (?, ?, 'CLIENT', ?, ?, ?)";
    private static final String UPDATE_MASTER_BY_ID =
            "UPDATE account " +
                    "SET login = ?, password = ?, name = ?, surname = ?, email = ? " +
                    "WHERE id = ? AND role= 'CLIENT'";

    @Override
    public Client getByLogin(String login) {
        Client client = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_CLIENT_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                client = extractClient(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return client;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_CLIENTS);
            Client client;
            while (rs.next()) {
                client = extractClient(rs);
                clientList.add(client);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return clientList;
    }

    @Override
    public Client getById(Long clientId) {
        Client client = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_CLIENT_BY_ID);
            ps.setLong(1, clientId);
            rs = ps.executeQuery();
            while (rs.next()) {
                client = extractClient(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return client;
    }

    @Override
    public boolean deleteById(Long clientId) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DELETE_CLIENT_BY_ID);
            ps.setLong(1, clientId);
            if (ps.executeUpdate() > 0) {
                result = true;
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
            }
        } finally {
            dbManager.close(con, ps);
        }
        return result;
    }

    @Override
    public Client insert(Client client) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(INSERT_NEW_CLIENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getLogin());
            ps.setString(2, client.getPassword());
            ps.setString(3, client.getName());
            ps.setString(4, client.getSurname());
            ps.setString(5, client.getEmail());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long clientId = rs.getLong(1);
                    client.setId(clientId);
                }
            }
            con.commit();
            log.debug("Recorded to bd : " + client);
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return client;
    }

    @Override
    public boolean update(Client client) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps = con.prepareStatement(UPDATE_MASTER_BY_ID);
            ps.setString(1, client.getLogin());
            ps.setString(2, client.getPassword());
            ps.setString(3, client.getName());
            ps.setString(4, client.getSurname());
            ps.setString(5, client.getEmail());
            ps.setLong(6, client.getId());
            if (ps.executeUpdate() > 0) {
                result = true;
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
            }
        } finally {
            dbManager.close(con, ps);
        }
        return result;
    }

    private Client extractClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong(Constant.ENTITY_ID));
        client.setLogin(rs.getString(Constant.ACCOUNT_LOGIN));
        client.setPassword(rs.getString(Constant.ACCOUNT_PASSWORD));
        client.setName(rs.getString(Constant.ACCOUNT_NAME));
        client.setSurname(rs.getString(Constant.ACCOUNT_SURNAME));
        client.setEmail(rs.getString(Constant.ACCOUNT_EMAIL));
        return client;
    }
}
