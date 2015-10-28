package edu.wctc.drn.bookwebapp.repository;

import edu.wctc.drn.bookwebapp.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Dan
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
}
