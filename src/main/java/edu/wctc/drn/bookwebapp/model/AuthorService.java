package edu.wctc.drn.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dnoonan1
 */
public class AuthorService {

    private DAO<Author> dao;

    public AuthorService(DAO<Author> dao) {
        this.dao = dao;
    }
    
    public boolean addAuthor(Author author) throws SQLException {
        return dao.add(author);
    }
    
    public boolean saveAuthor(Author author) throws SQLException {
        return dao.save(author);
    }
    
    public List<Author> getAllAuthors() throws SQLException {
        return dao.getAll();
    }
    
    public Author getAuthorById(int id) throws SQLException {
        return dao.getById(id);
    }
    
    public boolean deleteAuthor(int id) throws SQLException {
        return dao.deleteById(id);
    }
    
}
