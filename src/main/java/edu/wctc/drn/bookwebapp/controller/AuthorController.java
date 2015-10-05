package edu.wctc.drn.bookwebapp.controller;

import edu.wctc.drn.bookwebapp.model.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * The main controller for author-related activities
 *
 * @author jlombardo
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/author"})
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!
    private static final String DATABASE = "database";
    private static final String DRIVER_CLASS_NAME = "database.driverClassName";
    private static final String DATABASE_URL = "database.url";
    private static final String USER_NAME = "database.userName";
    private static final String PASSWORD = "database.password";
    private static final String DAO_CLASS_NAME = "dao.author";

    private static final String DATE_PATTERN = "MM/dd/yyyy";

    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String ADD_PAGE = "/addAuthor.jsp";
    private static final String EDIT_PAGE = "/updateAuthor.jsp";
//    private static final String DELETE_PAGE = "/deleteAuthor.jsp";
    private static final String STATS_PAGE = "/authorStats.jsp";

    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String EDIT_ACTION = "edit";
    private static final String DELETE_ACTION = "delete";
    private static final String STATS_ACTION = "stats";

    private static final String ACTION_PARAM = "action";
    private static final String ID_PARAM = "id";
    private static final String NAME_PARAM = "name";
    private static final String DATE_ADDED_PARAM = "dateAdded";

    private static final String AUTHOR_ATTR = "author";
    private static final String AUTHORS_ATTR = "authors";
    private static final String TIMESTAMP_ATTR = "timestamp";
    private static final String UPDATED_ATTR = "updated";
    private static final String STATS_ATTR = "stats";
    private static final String ERR_MSG_ATTR = "errMsg";

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";

    private ServletContext context;
    private AuthorService authorService;
    private String dbClassName;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private String daoClassName;
    
    @Override
    public void init() throws ServletException {
        context = this.getServletContext();
        dbClassName = context.getInitParameter(DATABASE);
        driverClassName = context.getInitParameter(DRIVER_CLASS_NAME);
        url = context.getInitParameter(DATABASE_URL);
        userName = context.getInitParameter(USER_NAME);
        password = context.getInitParameter(PASSWORD);
        daoClassName = context.getInitParameter(DAO_CLASS_NAME);
    }
    
    public void initAuthorService()
            throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NamingException {
            // Old Way (without DI)
        
//            Database db = new MySqlDatabase(
//                    "com.mysql.jdbc.Driver",
//                    "jdbc:mysql://localhost:3306/book",
//                    "root", "admin"
//            );
//            DAO<Author> authorDAO = new AuthorDAO(db);
            
            // New Way (with DI)
            Class dbClass = Class.forName(dbClassName);
            Class[] params = {
                driverClassName.getClass(),
                url.getClass(),
                userName.getClass(),
                password.getClass()
            };
            Constructor constructor = null;
            try {
                constructor = dbClass.getConstructor(params);
            } catch (NoSuchMethodException e) {
                // Just catch exception; constructor will remain null
            }
            Database db;
            if (constructor != null) {
                Object[] args = {driverClassName, url, userName, password};
                db = (Database)constructor.newInstance(args);
            } else { // Use DataSource object
                Context ctx = new InitialContext();
                DataSource ds = (DataSource)ctx.lookup("jdbc/book");
                db = (Database)dbClass.getConstructor(DataSource.class).newInstance(ds);
            }
            
            Class daoClass = Class.forName(daoClassName);
            DAO<Author> authorDAO = (DAO<Author>)daoClass
                    .getConstructor(Database.class)
                    .newInstance(db);
            
            authorService = new AuthorService(authorDAO);
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
        HttpSession session = request.getSession();
        AuthorStats stats = (AuthorStats)session.getAttribute(STATS_ATTR);
        if (stats == null) {
            stats = new AuthorStats();
        }
        
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        try {

//            Class dbClass = Class.forName(dbClassName);
//            Class[] params = {
//                driverClassName.getClass(),
//                url.getClass(),
//                userName.getClass(),
//                password.getClass()
//            };
//            Object[] args = {driverClassName, url, userName, password};
//            Database db = (Database)dbClass.getConstructor(params).newInstance(args);
//            
//            Class daoClass = Class.forName(daoClassName);
//            params = new Class[] {Database.class};
//            args = new Object[] {db};
//            DAO<Author> authorDAO = (DAO<Author>)daoClass
//                    .getConstructor(params)
//                    .newInstance(args);
//            
//            authorService = new AuthorService(authorDAO);
            
            if (authorService == null) {
                initAuthorService();
            }
            
            Author author;
            List<Author> authors;
            String sId;
            String name;
            String sDate;
            Date dateAdded;
            Date timestamp;
            
            switch (action) {
                
                case LIST_ACTION:
                    authors = authorService.getAllAuthors();
                    request.setAttribute(AUTHORS_ATTR, authors);
                    stats.setAuthorCount(authors.size());
                    destination = LIST_PAGE;
                    break;
                    
                case ADD_ACTION:
                    name = request.getParameter(NAME_PARAM);
                    if (name != null) {
                        author = new Author(name);
                        authorService.addAuthor(author);
                        timestamp = new Date();
//                        authors = authorService.getAllAuthors();
//                        request.setAttribute(AUTHORS_ATTR, authors);
                        request.setAttribute(AUTHOR_ATTR, author);
                        request.setAttribute(TIMESTAMP_ATTR, timestamp);
                        stats.addAuthor();
                    }
                    destination = ADD_PAGE;
                    break;
                    
                case EDIT_ACTION:
                    sId = request.getParameter(ID_PARAM);
                    int id = Integer.parseInt(sId);
                    name = request.getParameter(NAME_PARAM);
                    sDate = request.getParameter(DATE_ADDED_PARAM);
                    if (name == null || sDate == null) {
                        author = authorService.getAuthorById(id);   
                    } else {
                        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
                        dateAdded = df.parse(sDate);
                        author = new Author(id, name, dateAdded);
                        authorService.saveAuthor(author);
                        timestamp = new Date();
                        Date d = new Date();
                        request.setAttribute(UPDATED_ATTR, true);
                        request.setAttribute(TIMESTAMP_ATTR, timestamp);
                        stats.editAuthor();
                    }
                    request.setAttribute(AUTHOR_ATTR, author);
                    destination = EDIT_PAGE;
                    break;
                        
                case DELETE_ACTION:
                    String[] ids = request.getParameterValues(ID_PARAM);
                    for (String s : ids) {
                        authorService.deleteAuthor(Integer.parseInt(s));
                        stats.deleteAuthor();
                    }
                    authors = authorService.getAllAuthors();
                    request.setAttribute(AUTHORS_ATTR, authors);
                    destination = LIST_PAGE;
                    break;
                
                case STATS_ACTION:
                    destination = STATS_PAGE;
                    break;
                
                default:
                    // No param identified in request, must be an error
                    request.setAttribute(ERR_MSG_ATTR, NO_PARAM_ERR_MSG);
            }

        } catch (Exception e) {
            request.setAttribute(ERR_MSG_ATTR, e.getMessage());
            // This will print the stack trace on the response page
            /*try (PrintWriter out = response.getWriter()) {
                e.printStackTrace(out);
            }*/
        }

        session.setAttribute(STATS_ATTR, stats);
        
        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
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
