package edu.wctc.drn.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dan Noonan
 */
public interface DAO<T> {
    boolean add(T t) throws SQLException;
    boolean save(T t) throws SQLException;
    List<T> getAll() throws SQLException;
    T getById(Object id) throws SQLException;
    boolean deleteById(Object id) throws SQLException;
}
