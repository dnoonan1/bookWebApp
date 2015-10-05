package edu.wctc.drn.bookwebapp.model;

/**
 * @author Dan Noonan
 */
public class AuthorStats {

    private int authorCount;
    private int authorsAdded;
    private int editCount;
    private int authorsDeleted;

    public int getAuthorCount() {
        return authorCount;
    }

    public void setAuthorCount(int authorCount) {
        this.authorCount = authorCount;
    }
    
    public void addAuthor() {
        this.authorCount++;
        this.authorsAdded++;
    }
    
    public void deleteAuthor() {
        this.authorCount--;
        this.authorsDeleted++;
    }
    
    public void editAuthor() {
        this.editCount++;
    }

    public int getAuthorsAdded() {
        return authorsAdded;
    }
    public int getEditCount() {
        return editCount;
    }

    public int getAuthorsDeleted() {
        return authorsDeleted;
    }
    
}
