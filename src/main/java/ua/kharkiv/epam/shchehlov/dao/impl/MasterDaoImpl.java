package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.MasterDao;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Master;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasterDaoImpl implements MasterDao {
    private static final Logger log = Logger.getLogger(MasterDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_MASTERS =
            "SELECT a.id a_id," +
                    "a.login a_login," +
                    "a.password a_password," +
                    "a.name a_name," +
                    "a.surname a_surname," +
                    "a.email a_email," +
                    "(SELECT AVG(r.rate) FROM meeting m " +
                    "JOIN review r ON m.review_id = r.id " +
                    "JOIN master_service ms " +
                    "ON m.master_service_id = ms.id " +
                    "WHERE ms.account_id = a_id " +
                    "GROUP BY ms.account_id) avg_rate " +
                    "FROM account a WHERE role = 'MASTER'";
    private static final String FIND_MASTER_BY_ID = FIND_ALL_MASTERS + " AND a.id = ?";
    private static final String FIND_MASTER_BY_SURNAME = FIND_ALL_MASTERS + " AND surname = ?";
    private static final String DELETE_MASTER_BY_ID = "DELETE FROM account WHERE id = ?";
    private static final String INSERT_NEW_MASTER =
            "INSERT INTO account " +
                    "(login, password, role, name, surname, email) " +
                    "VALUES (?, ?, 'MASTER', ?, ?, ?)";
    private static final String UPDATE_MASTER_BY_ID =
            "UPDATE account " +
                    "SET login = ?, password = ?, name = ?, surname = ?, email = ? " +
                    "WHERE id = ? AND role= 'MASTER'";

    @Override
    public Master getBySurname(String surname) {
        Master master = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_MASTER_BY_SURNAME);
            ps.setString(1, surname);
            rs = ps.executeQuery();
            while (rs.next()) {
                master = extractMaster(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return master;
    }

    @Override
    public List<Master> getAll() {
        List<Master> masterList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_MASTERS);
            Master master;
            while (rs.next()) {
                master = extractMaster(rs);
                masterList.add(master);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return masterList;
    }

    @Override
    public Master getById(Long masterId) {
        Master master = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_MASTER_BY_ID);
            ps.setLong(1, masterId);
            rs = ps.executeQuery();
            while (rs.next()) {
                master = extractMaster(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return master;
    }

    @Override
    public boolean deleteById(Long id) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DELETE_MASTER_BY_ID);
            ps.setLong(1, id);
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
    public Master insert(Master master) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(INSERT_NEW_MASTER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, master.getLogin());
            ps.setString(2, master.getPassword());
            ps.setString(3, master.getName());
            ps.setString(4, master.getSurname());
            ps.setString(5, master.getEmail());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long serviceId = rs.getLong(1);
                    master.setId(serviceId);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return master;
    }

    @Override
    public boolean update(Master master) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps = con.prepareStatement(UPDATE_MASTER_BY_ID);
            ps.setString(1, master.getLogin());
            ps.setString(2, master.getPassword());
            ps.setString(3, master.getName());
            ps.setString(4, master.getSurname());
            ps.setString(5, master.getEmail());
            ps.setLong(6, master.getId());
            if (ps.executeUpdate() > 0) {
                result = true;
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error("cannot obtain categories", ex);
            }
        } finally {
            dbManager.close(con, ps);
        }
        return result;
    }

    private Master extractMaster(ResultSet rs) throws SQLException {
        Master master = new Master();
        master.setId(rs.getLong(Constant.MASTER_ID));
        master.setLogin(rs.getString(Constant.MASTER_LOGIN));
        master.setPassword(rs.getString(Constant.MASTER_PASSWORD));
        master.setName(rs.getString(Constant.MASTER_NAME));
        master.setSurname(rs.getString(Constant.MASTER_SURNAME));
        master.setEmail(rs.getString(Constant.MASTER_EMAIL));
        master.setRate(rs.getDouble(Constant.MASTER_RATE));
        return master;
    }


}
