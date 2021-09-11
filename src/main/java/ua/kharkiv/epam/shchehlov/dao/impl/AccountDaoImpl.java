package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.AccountDao;
import ua.kharkiv.epam.shchehlov.dao.db.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Account;
import ua.kharkiv.epam.shchehlov.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static final Logger log = Logger.getLogger(AccountDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String FIND_ACCOUNT_BY_LOGIN = "SELECT * FROM account WHERE login = ?";
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
    public List<Account> getAll() {
        List<Account> accountList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_ACCOUNTS);
            Account account;
            while (rs.next()) {
                account = extractAccount(rs);
                accountList.add(account);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return accountList;
    }

    @Override
    public Account getById(Long accountId) {
        Account account = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_ACCOUNT_BY_ID);
            ps.setLong(1, accountId);
            rs = ps.executeQuery();
            while (rs.next()) {
                account = extractAccount(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return account;
    }

    @Override
    public Account getByLogin(String login) {
        Account account = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_ACCOUNT_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                account = extractAccount(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        log.debug(String.format("account created login- %s, password - %s", account.getLogin(), account.getPassword() ));
        return account;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Account insert(Account item) {
        return null;
    }

    @Override
    public boolean update(Account item) {
        return false;
    }

    private Account extractAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong(Constant.ENTITY_ID));
        account.setLogin(rs.getString(Constant.ACCOUNT_LOGIN));
        account.setPassword(rs.getString(Constant.ACCOUNT_PASSWORD));
        switch (rs.getString(Constant.ACCOUNT_ROLE)) {
            case "ADMIN":
                account.setRole(Role.ADMIN);
                break;
            case "MASTER":
                account.setRole(Role.MASTER);
                break;
            case "CLIENT":
                account.setRole(Role.CLIENT);
                break;
        }
        account.setName(rs.getString(Constant.ACCOUNT_NAME));
        account.setSurname(rs.getString(Constant.ACCOUNT_SURNAME));
        account.setEmail(rs.getString(Constant.ACCOUNT_EMAIL));
        return account;
    }
}
