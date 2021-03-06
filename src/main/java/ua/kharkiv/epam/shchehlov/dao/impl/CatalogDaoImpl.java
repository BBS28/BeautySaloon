package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.CatalogDao;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogDaoImpl implements CatalogDao {
    private static final Logger log = Logger.getLogger(CatalogDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_MASTER_SERVICE = "SELECT * FROM master_service";
    private static final String FIND_MASTER_SERVICE_BY_ID = "SELECT * FROM master_service WHERE id = ?";
    private static final String DELETE_MASTER_SERVICE_BY_ID = "DELETE FROM master_service WHERE id = ?";
    private static final String INSERT_NEW_MASTER_SERVICE = "INSERT INTO master_service (account_id, service_id) VALUES (?, ?)";

    @Override
    public Catalog getByMasterId(Long masterId) {
        return null;
    }

    @Override
    public Catalog getByServiceId(Long serviceId) {
        return null;
    }

    @Override
    public List<Catalog> getAll() {
        List<Catalog> msList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_MASTER_SERVICE);
            Catalog ms;
            while (rs.next()) {
                ms = extractMasterService(rs);
                msList.add(ms);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return msList;
    }

    @Override
    public Catalog getById(Long msId) {
        Catalog ms = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_MASTER_SERVICE_BY_ID);
            ps.setLong(1, msId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ms = extractMasterService(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return ms;
    }

    @Override
    public boolean deleteById(Long id) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DELETE_MASTER_SERVICE_BY_ID);
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
    public Catalog insert(Catalog ms) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(INSERT_NEW_MASTER_SERVICE, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ms.getMaster().getId());
            ps.setLong(2, ms.getService().getId());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long serviceId = rs.getLong(1);
                    ms.setId(serviceId);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            log.error(Constant.ERROR_CANNOT_OBTAIN_CATEGORIES, ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return ms;
    }

    @Override
    public boolean update(Catalog item) {
        return false;
    }

    private Catalog extractMasterService(ResultSet rs) throws SQLException {
        Catalog ms = new Catalog();
        ms.setId(rs.getLong(Constant.ENTITY_ID));
        ms.setMaster(new MasterDaoImpl().getById(rs.getLong(Constant.MASTER_SERVICE_ACCOUNT_ID)));
        ms.setService(new ServiceDaoImpl().getById(rs.getLong(Constant.MASTER_SERVICE_SERVICE_ID)));
        return ms;
    }
}
