package edu.wctc.drn.bookwebapp.service;

import edu.wctc.drn.bookwebapp.entity.Book;
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
@Repository("bookService")
@Transactional(readOnly = true)
public class BookService {
    private transient final Logger LOG = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;

    public BookService() {
    }
    
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    
    public Book find(Integer id) {
        return bookRepo.findOne(id);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Book book) {
        LOG.debug("Deleting book: " + book.getTitle());
        bookRepo.delete(book);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Book edit(Book book) {
        return bookRepo.saveAndFlush(book);
    }
    
}
