package edu.wctc.drn.bookwebapp.repository;

import edu.wctc.drn.bookwebapp.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Dan
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
