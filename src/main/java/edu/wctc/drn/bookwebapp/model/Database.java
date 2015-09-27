package edu.wctc.drn.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author dnoonan1
 */
public interface Database {

    void openConnection() throws SQLException;
    void closeConnection() throws SQLException;

    List<Map<String, Object>> getAllRecords(String tableName)
            throws SQLException;
    
    Map<String, Object> getByPrimaryKey(String tableName,
            String primaryKeyName, Object primaryKeyValue) throws SQLException;
    
    boolean insertRecord(String tableName,
            List<String> columnNames, List columnValues) throws SQLException;
    
    boolean updateRecord(String tableName,
            List<String> columnNames, List columnValues, 
            String whereField, Object whereValue) throws SQLException;
    
    boolean deleteByPrimaryKey(String tableName,
            String primaryKeyName, Object primaryKeyValue) throws SQLException;
    
}
