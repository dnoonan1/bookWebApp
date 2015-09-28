package edu.wctc.drn.bookwebapp.model;

import java.sql.SQLException;
import java.util.*;

/**
 * @author dnoonan1
 */
public class AuthorDAO implements DAO<Author> {

    private final Database db;
    
    private static final String TABLE_NAME = "author";
    private static final String AUTHOR_ID = "author_id";
    private static final String AUTHOR_NAME = "author_name";
    private static final String DATE_ADDED = "date_added";

    public AuthorDAO(Database db) {
        this.db = db;
    }
    
    private void openConnection() throws SQLException {
        db.openConnection();
    }
    
    private void closeConnection() throws SQLException {
        db.closeConnection();
    }
    
    @Override
    public boolean add(Author author) throws SQLException {
        if (author.getAuthorName() == null && author.getDateAdded() == null) {
            return false;
        }
        List<String> columns = Arrays.asList(
                AUTHOR_NAME,
                DATE_ADDED
        );
        List values = Arrays.asList(
                author.getAuthorName(),
                author.getDateAdded()
        );
        openConnection();
        boolean result = db.insertRecord(TABLE_NAME, columns, values);
        closeConnection();
        return result;
    }
    
    @Override
    public boolean save(Author author) throws SQLException {
        if (author.getAuthorName() == null && author.getDateAdded() == null) {
            return false;
        }
        List<String> columns = Arrays.asList(
                AUTHOR_NAME,
                DATE_ADDED
        );
        List values = Arrays.asList(
                author.getAuthorName(),
                author.getDateAdded()
        );
        if (author.getAuthorName() == null) {
            columns.remove(AUTHOR_NAME);
            values.remove(null);
        }
        if (author.getDateAdded() == null) {
            columns.remove(AUTHOR_NAME);
            values.remove(null);
        }
        openConnection();
        boolean result = db.updateRecord(TABLE_NAME, columns, values, AUTHOR_ID, author.getAuthorId());
        closeConnection();
        return result;
    }
    
    @Override
    public List<Author> getAll() throws SQLException {
        openConnection();
        List<Map<String, Object>> records = db.getAllRecords(TABLE_NAME);
        closeConnection();
        List<Author> authors = new ArrayList<>();
        for (Map<String, Object> record : records) {
            Author author = new Author(
                (int)record.get(AUTHOR_ID),
                (String)record.get(AUTHOR_NAME),
                (Date)record.get(DATE_ADDED)
            );
            authors.add(author);
        }
        return authors;
    }

    @Override
    public Author getById(Object id) throws SQLException {
        openConnection();
        Map<String, Object> record = db.getByPrimaryKey(TABLE_NAME, AUTHOR_ID, id);
        closeConnection();
        if (record != null) {
            Object authorId = record.get(AUTHOR_ID);
            Object authorName = record.get(AUTHOR_NAME);
            Object dateAdded = record.get(DATE_ADDED);
            return new Author(
                    authorId == null ? null :(Integer)authorId,
                    authorName == null ? null : (String)authorName,
                    dateAdded == null ? null : (Date)dateAdded
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteById(Object id) throws SQLException {
        openConnection();
        boolean result = db.deleteByPrimaryKey(TABLE_NAME, AUTHOR_ID, id);
        closeConnection();
        return result;
    }
    
}
