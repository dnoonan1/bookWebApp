package edu.wctc.drn.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dnoonan1
 */
public class DriverManagerConnector implements DatabaseConnector {
    
    private final String driverClassName;
    private final String url;
    private final String user;
    private final String password;

    public DriverManagerConnector(String driverClassName, String url, String user, String password)
            throws ClassNotFoundException {
        Class.forName(driverClassName);
        this.driverClassName = driverClassName;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    
}
