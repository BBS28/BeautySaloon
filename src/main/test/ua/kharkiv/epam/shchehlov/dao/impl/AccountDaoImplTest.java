package ua.kharkiv.epam.shchehlov.dao.impl;

import org.junit.Before;
import org.junit.Test;
import ua.kharkiv.epam.shchehlov.entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountDaoImplTest {
    private AccountDaoImpl accountDao;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    Account account;

    @Before
    public void setUp(){
        accountDao = new AccountDaoImpl();
        account = mock(Account.class);
        connection = mock(Connection.class);
        statement = mock(Statement.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void getAll() throws SQLException {
//        List<Account> accountList = new ArrayList<>();
//        when(connection.createStatement()).thenReturn(statement);
//        when(statement.executeQuery(anyString())).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(false);
//        assertEquals(accountList, accountDao.getAll());
    }
}