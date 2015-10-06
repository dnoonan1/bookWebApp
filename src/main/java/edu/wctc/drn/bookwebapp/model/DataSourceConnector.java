package edu.wctc.drn.bookwebapp.model;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Dan Noonan
 */
public class DataSourceConnector implements DatabaseConnector {

    private DataSource ds;
    
    public DataSourceConnector(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
}
