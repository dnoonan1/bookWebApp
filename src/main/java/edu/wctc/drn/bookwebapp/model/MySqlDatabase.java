package edu.wctc.drn.bookwebapp.model;

import java.sql.*;
import java.util.*;

/**
 * @author dnoonan1
 */
public class MySqlDatabase implements Database {
    
    // Will this ever change?
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    
    private final String url;
    private final String userName;
    private final String password;
    private Connection conn;
    
    // For testing whether Create, Update, and Delete operations are successful
    private static final int SUCCESS = 1;
    
    // SQL constants
    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final char BEGIN_LIST = '(';
    private static final char END_LIST = ')';
    private static final String COMMA = ", ";
    private static final String VALUES = " VALUES ";
    private static final String UPDATE = "UPDATE ";
    private static final String SET = " SET ";
    private static final String DELETE_FROM = "DELETE FROM ";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = " = ";
    private static final String PARAMETER = "?";
    private static final char SEMICOLON = ';';

    public MySqlDatabase(String url, String userName, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        this.url = url;
        this.userName = userName;
        this.password = password;
        /* Try connecting to database on construction, rather than waiting
           until a CRUD operation is attempted. I.e., fail early if the url,  
           userName, or password are incorrect. */
        openConnection();
        closeConnection();
    }
    
    @Override
    public final void openConnection() throws SQLException  {
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public final void closeConnection() throws SQLException {
        conn.close();
    }
    
    /*
     * CRUD Methods (Create, Retrieve, Update, Delete)
     */
    
    /* CRUD - Create (INSERT) */
    
    @Override
    public final boolean insertRecord(String tableName,
            List<String> columnNames, List columnValues) 
            throws SQLException {
        PreparedStatement pstmt = buildInsertStatement(tableName, columnNames);
        int i = 1;
        Iterator it = columnValues.iterator();
        while (it.hasNext()) {
            pstmt.setObject(i++, it.next());
        }
        return pstmt.executeUpdate() == SUCCESS;
    }
    
    /* CRUD - Retrieve (SELECT) */
    
    @Override
    public final List<Map<String,Object>> getAllRecords(String tableName)
            throws SQLException {
        List<Map<String,Object>> records = new ArrayList<>();
        
        String sql = SELECT_ALL_FROM + tableName + SEMICOLON;        
         
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String,Object> record = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
                records.add(record);
            }
        } finally {
            return records;
        }
        
    }
    
    @Override
    public Map<String, Object> getByPrimaryKey(String tableName,
            String primaryKeyName, Object primaryKeyValue)
            throws SQLException {
        
        String sql = SELECT_ALL_FROM + tableName
                + WHERE + primaryKeyName + EQUALS + PARAMETER + SEMICOLON;
            
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, primaryKeyValue);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
                return record;
            } else {
                return null;
            }
        } finally {}
        
    }
    
    /* CRUD - Update */
    
    @Override
    public boolean updateRecord(String tableName,
            List<String> columnNames, List columnValues,
            String whereField, Object whereValue) throws SQLException {
        
        if (!columnNames.isEmpty()) {
            PreparedStatement pstmt = buildUpdateStatement(tableName,
                    columnNames, whereField);

            // Set parameter column values
            int i = 1;
            Iterator it = columnValues.iterator();
            while (it.hasNext()) {
                pstmt.setObject(i++, it.next());
            }
            // Set parameter WHERE value
            pstmt.setObject(i, whereValue);
            return pstmt.executeUpdate() == SUCCESS;
        } else {
            return false;
        }
    }
    
    
    /* CRUD - Delete */
    
    // Statement
//    public boolean deleteByPrimaryKey(String tableName, String primaryKeyName,
//            Object primaryKeyValue) throws SQLException {
//        
//        boolean recordDeleted;
//        String sql = DELETE_FROM + tableName
//                + WHERE + primaryKeyName + EQUALS;
//        
//        if (primaryKeyValue instanceof String) {
//            sql += "'" + primaryKeyValue + "'";
//        } else {
//            sql += primaryKeyValue;
//        }
//        sql += SEMICOLON;
//                        
//            // *** FOR TESTING PURPOSES ***
//            System.out.println('\n' + sql + '\n');
//            // *** DELETE AFTER TESTING ***
//        
//        try (Statement stmt = conn.createStatement()) {
//            recordDeleted = stmt.executeUpdate(sql) == SUCCESS;
//            return recordDeleted;
//        } catch (SQLException e) {
//            throw e;
//        }
//    }
    
    // Prepared Statement
    @Override
    public boolean deleteByPrimaryKey(String tableName, String primaryKeyName,
            Object primaryKeyValue) throws SQLException {
        
        boolean recordDeleted;
        String sql = DELETE_FROM + tableName
                + WHERE + primaryKeyName + EQUALS + PARAMETER + SEMICOLON;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, primaryKeyValue);
            recordDeleted = pstmt.executeUpdate() == SUCCESS;
            return recordDeleted;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    
    /*
     * Helper Methods
     * (for building SQL statements)
     */
    
    private PreparedStatement buildInsertStatement(String tableName, List<String> columnNames)
            throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(INSERT_INTO).append(tableName).append(BEGIN_LIST).append(columnNames.get(0));
        for (int i = 1; i < columnNames.size(); i++) {
            sb.append(COMMA).append(columnNames.get(i));
        }
        sb.append(END_LIST).append(VALUES).append(BEGIN_LIST).append(PARAMETER);
        for (int i = 1; i < columnNames.size(); i++) {
            sb.append(COMMA).append(PARAMETER);
        }
        sb.append(END_LIST).append(SEMICOLON);
        String sql = sb.toString();
            
        return conn.prepareStatement(sql);
    }
    
    private PreparedStatement buildUpdateStatement(String tableName,
            List<String> columnNames, String whereField)
            throws SQLException {
        
        String sql;
        StringBuffer sb = new StringBuffer();
        
        // UPDATE table SET column-1=?
        sb.append(UPDATE).append(tableName).append(SET)
                .append(columnNames.get(0)).append(EQUALS).append(PARAMETER);
        
        // ,column-2=?,column-3=?, ... ,column-n=?
        for (int i = 1; i < columnNames.size(); i++) {
            sb.append(COMMA).append(columnNames.get(i))
                    .append(EQUALS).append(PARAMETER);
        }
        sb.append(WHERE).append(whereField).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        
        sql = sb.toString();       
        return conn.prepareStatement(sql);
    }
    
}
