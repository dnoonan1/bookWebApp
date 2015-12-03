package edu.wctc.drn.bookwebapp.repository;

import edu.wctc.drn.bookwebapp.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Dan
 */
@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
}
