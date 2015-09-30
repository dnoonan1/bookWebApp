package edu.wctc.drn.bookwebapp.model;

import java.util.Date;

/**
 * @author dnoonan1
 */
public class Author {

    private Integer id;
    private String name;
    private Date dateAdded;

    public Author(String name) {
        this.id = null;
        this.name = name;
        this.dateAdded = new Date();
    }
    
    public Author(String name, Date dateAdded) {
        this.id = null;
        this.name = name;
        this.dateAdded = dateAdded;
    }
    
    public Author(int id, String name, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int authorId) {
        this.id = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String authorName) {
        this.name = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    @Override
    public String toString() {
        return id + " " + name + " " + dateAdded;
    }
    
}
