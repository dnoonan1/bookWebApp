package edu.wctc.drn.bookwebapp.model;

import java.util.Date;

/**
 * @author dnoonan1
 */
public class Author {

    private Integer authorId;
    private String authorName;
    private Date dateAdded;

    public Author(String authorName, Date dateAdded) {
        this.authorId = null;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }
    
    public Author(int authorId, String authorName, Date dateAdded) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    @Override
    public String toString() {
        return authorId + " " + authorName + " " + dateAdded;
    }
    
}
