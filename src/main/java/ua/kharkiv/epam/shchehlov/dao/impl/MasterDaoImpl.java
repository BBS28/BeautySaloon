package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.MasterDao;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MasterDaoImpl implements MasterDao {
    private static final Logger log = Logger.getLogger(MasterDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_MASTERS = "SELECT * FROM account WHERE role = ";

    @Override
    public Master getBySurname(String surname) {
        return null;
    }

    @Override
    public Master getByRate(double rate) {
        return null;
    }

    @Override
    public List getAll() {
        Master master = null;
        List<Master> masters = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_MASTERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Master getById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Master insert(Master item) {
        return null;
    }

    @Override
    public boolean update(Master item) {
        return false;
    }
}
