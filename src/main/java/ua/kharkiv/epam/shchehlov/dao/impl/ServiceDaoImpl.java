package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.ServiceDao;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {
    private static final Logger log = Logger.getLogger(ServiceDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();


    private static final String FIND_ALL_SERVICES = "SELECT * FROM service";
    private static final String FIND_SERVICE_BY_ID = "SELECT id, name, duration, price FROM service WHERE id = ?";
    private static final String FIND_SERVICE_BY_NAME = "SELECT id, name, duration, price FROM service WHERE name = ?";
    private static final String INSERT_NEW_SERVICE = "INSERT INTO service (name, duration, price) VALUES (?, ?, ?)";
    private static final String UPDATE_SERVICE_BY_ID = "UPDATE service SET name = ?, duration = ?, price = ? WHERE id = ?";
    private static final String DELETE_SERVICE_BY_ID = "DELETE FROM service WHERE id = ?";

    @Override
    public List<Service> getAll() {
        List<Service> serviceList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_SERVICES);
            Service service;
            while (rs.next()) {
                service = extractService(rs);
                serviceList.add(service);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return serviceList;
    }

    @Override
    public Service getById(Long serviceId) {
        Service service = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_SERVICE_BY_ID);
            ps.setLong(1, serviceId);
            rs = ps.executeQuery();
            while (rs.next()) {
                service = extractService(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return service;
    }

    @Override
    public Service getByName(String serviceName) {
        Service service = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_SERVICE_BY_NAME);
            ps.setString(1, serviceName);
            rs = ps.executeQuery();
            while (rs.next()) {
                service = extractService(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return service;
    }

    @Override
    public boolean deleteById(Long id) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DELETE_SERVICE_BY_ID);
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
    public Service insert(Service service) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(INSERT_NEW_SERVICE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getName());
            ps.setInt(2, service.getDuration());
            ps.setDouble(3, service.getPrice());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long serviceId = rs.getLong(1);
                    service.setId(serviceId);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return service;
    }

    @Override
    public boolean update(Service service) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps = con.prepareStatement(UPDATE_SERVICE_BY_ID);
            ps.setString(1, service.getName());
            ps.setInt(2, service.getDuration());
            ps.setDouble(3, service.getPrice());
            ps.setLong(4, service.getId());
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

    private Service extractService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getLong(Constant.ENTITY_ID));
        service.setName(rs.getString(Constant.SERVICE_NAME));
        service.setDuration(rs.getInt(Constant.SERVICE_DURATION));
        service.setPrice(rs.getDouble(Constant.SERVICE_PRICE));
        return service;
    }

}
