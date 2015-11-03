package edu.wctc.drn.bookwebapp.controller;

import edu.wctc.drn.bookwebapp.entity.Author;
import edu.wctc.drn.bookwebapp.entity.Book;
import edu.wctc.drn.bookwebapp.service.AuthorService;
import edu.wctc.drn.bookwebapp.service.BookService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author dnoonan1
 */
@WebServlet(name = "BookController", urlPatterns = {"/book"})
public class BookController extends HttpServlet {

    private static final String LIST_PAGE = "/bookList.jsp";
    private static final String ADD_PAGE = "/bookUpdate.jsp";
    private static final String EDIT_PAGE = ADD_PAGE;
    private static final String STATS_PAGE = "/bookStats.jsp";
    
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_SUBACTION = "subaction";
    private static final String PARAM_ID = "id";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_ISBN = "isbn";
    private static final String PARAM_AUTHOR_ID = "authorId";
    
    private static final String ACTION_LIST = "list";
    private static final String ACTION_ADD = "add";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_SAVE = "save";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_VIEW_STATS = "viewStats";
    
    private static final String ATTR_BOOK = "book";
    private static final String ATTR_BOOKS = "books";
    private static final String ATTR_AUTHOR = "author";
    private static final String ATTR_AUTHORS = "authors";
    
    private ServletContext appContext;
    private BookService bookService;
    private AuthorService authorService;
    
    @Override
    public void init() throws ServletException {
        appContext = this.getServletContext();
    }
    
    public void initAuthorService() {
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(appContext);
        authorService = (AuthorService)ctx.getBean("authorService");
    }
    
    public void initBookService() {
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(appContext);
        bookService = (BookService)ctx.getBean("bookService");
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        if (bookService == null) {
            initBookService();
        }
        if (authorService == null) {
            initAuthorService();
        }
        
        String destination = LIST_PAGE;
        String action = request.getParameter(PARAM_ACTION);
        String subaction = request.getParameter(PARAM_SUBACTION);
        List<String> errors = new ArrayList<>();
        Book book;
        
        switch (action) {
            
            case ACTION_ADD:
                if (subaction != null && subaction.equals(ACTION_SAVE)) {
                    addBook(request, errors);
                }
                findAllAuthors(request, errors);
                destination = ADD_PAGE;
                break;
            
            case ACTION_EDIT:
                if (subaction == null) {
                    findBook(request, errors);
                } else if (subaction.equals(ACTION_SAVE)) {
                    editBook(request, errors);
                }
                findAllAuthors(request, errors);
                destination = EDIT_PAGE;
                break;
            
            case ACTION_DELETE:
                deleteBooks(request, errors);
//                destination = LIST_PAGE; // already set
                break;
            
            case ACTION_VIEW_STATS:
                destination = STATS_PAGE;
                break;
                
            case ACTION_LIST:
            default:
                listBooks(request);
//                destination = LIST_PAGE; // already set
                break;
            
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
    
    private void listBooks(HttpServletRequest request) {
        List<Book> books = bookService.findAll();
        request.setAttribute(ATTR_BOOKS, books);
    }
    
    private void addBook(HttpServletRequest request, List<String> errors) {
        Book book = new Book();
        updateBook(request, book, errors);
    }
    
    private void editBook(HttpServletRequest request, List<String> errors) {
        Integer id = new Integer(request.getParameter(PARAM_ID));
        Book book = new Book(id);
        updateBook(request, book, errors);
    }
    
    private void updateBook(HttpServletRequest request, Book book, List<String> errors) {
        String title = request.getParameter(PARAM_TITLE);
        String isbn = request.getParameter(PARAM_ISBN);
        Integer authorId = new Integer(request.getParameter(PARAM_AUTHOR_ID));
        Author author = authorService.find(authorId);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthorId(author);
        if (book.getBookId() == null) {
            bookService.edit(book);
        } else {
            bookService.edit(book);
        }
    }
    
    private void deleteBooks(HttpServletRequest request, List<String> errors) {
        String[] ids = request.getParameterValues(PARAM_ID);
        for (String id : ids) {
            Book book = bookService.find(new Integer(id));
            bookService.remove(book);
        }
        listBooks(request);
    }
    
    private void findBook(HttpServletRequest request, List<String> errors) {
        String id = request.getParameter(PARAM_ID);
        // TODO: Exception handling
        Book book = bookService.find(new Integer(id));
        request.setAttribute(ATTR_BOOK, book);
    }
    
    private void findAllAuthors(HttpServletRequest request, List<String> errors) {
        List<Author> authors = authorService.findAll();
        request.setAttribute(ATTR_AUTHORS, authors);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
