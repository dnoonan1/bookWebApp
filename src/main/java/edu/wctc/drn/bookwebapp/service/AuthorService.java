package edu.wctc.drn.bookwebapp.service;

import edu.wctc.drn.bookwebapp.entity.Author;
import edu.wctc.drn.bookwebapp.repository.AuthorRepository;
import edu.wctc.drn.bookwebapp.repository.BookRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dan
 */
@Repository("authorService")
@Transactional(readOnly = true)
public class AuthorService {

    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookRepository bookRepo;

    public AuthorService() {
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public Author find(Integer id) {
        return authorRepo.findOne(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        authorRepo.delete(author);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Author edit(Author author) {
        return authorRepo.saveAndFlush(author);
    }
}
