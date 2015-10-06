package edu.wctc.drn.bookwebapp.model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dnoonan1
 */
public interface DatabaseConnector {
    Connection getConnection() throws SQLException;
}
