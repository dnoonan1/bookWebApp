package edu.wctc.drn.bookwebapp.controller;

import edu.wctc.drn.bookwebapp.model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private static final String UPDATE_PAGE = "/updateAuthor.jsp";
    private static final String DELETE_PAGE = "/deleteAuthor.jsp";

    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";

    private static final String ACTION_PARAM = "action";
    private static final String AUTHOR_NAME_PARAM = "authorName";
    private static final String AUTHOR_ID_PARAM = "authorId";
    private static final String DATE_ADDED_PARAM = "dateAdded";

    private static final String AUTHOR_ATTR = "author";
    private static final String AUTHORS_ATTR = "authors";
    private static final String AUTHOR_ID_ATTR = "authorId";
    private static final String AUTHOR_NAME_ATTR = "authorName";
    private static final String DATE_ADDED_ATTR = "dateAdded";
    private static final String ERR_MSG_ATTR = "errMsg";

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";

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

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        ServletContext context = this.getServletContext();
        String dbClassName = context.getInitParameter(DATABASE);
        String driverClassName = context.getInitParameter(DRIVER_CLASS_NAME);
        String url = context.getInitParameter(DATABASE_URL);
        String userName = context.getInitParameter(USER_NAME);
        String password = context.getInitParameter(PASSWORD);
        String daoClassName = context.getInitParameter(DAO_CLASS_NAME);
        
        try {
            
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
            Object[] args = {driverClassName, url, userName, password};
            Database db = (Database)dbClass.getConstructor(params).newInstance(args);
            
            Class daoClass = Class.forName(daoClassName);
            params = new Class[] {Database.class};
            args = new Object[] {db};
            DAO<Author> authorDAO = (DAO<Author>)daoClass
                    .getConstructor(params)
                    .newInstance(args);
            
            AuthorService authorService = new AuthorService(authorDAO);

            if (action.equals(LIST_ACTION)) {

                List<Author> authors;
                authors = authorService.getAllAuthors();
                request.setAttribute(AUTHORS_ATTR, authors);
                destination = LIST_PAGE;

            } else if (action.equals(ADD_ACTION)) {

                String name = request.getParameter(AUTHOR_NAME_PARAM);
                if (name != null) {
                    Author author = new Author(name, new Date());
                    authorService.addAuthor(author);
                    request.setAttribute(AUTHOR_ATTR, author);
                }
                destination = ADD_PAGE;

            } else if (action.equals(UPDATE_ACTION)) {

                String sId = request.getParameter(AUTHOR_ID_PARAM);
                if (sId != null) {
                    int id = Integer.parseInt(sId);
                    String name = request.getParameter(AUTHOR_NAME_PARAM);
                    String sDate = request.getParameter(DATE_ADDED_PARAM);
                    SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
                    Date dateAdded = df.parse(sDate);
                    Author author = new Author(id, name, dateAdded);
                    request.setAttribute(AUTHOR_ATTR, author);
                    authorService.saveAuthor(author);
                }
                destination = UPDATE_PAGE;

            } else if (action.equals(DELETE_ACTION)) {

                String sId = request.getParameter(AUTHOR_ID_PARAM);
                if (sId != null) {
                    int id = Integer.parseInt(sId);
                    request.setAttribute(AUTHOR_ID_ATTR, id);
                    authorService.deleteAuthor(id);
                }
                destination = DELETE_PAGE;

            } else {
                // No param identified in request, must be an error
                request.setAttribute(ERR_MSG_ATTR, NO_PARAM_ERR_MSG);
            }

        } catch (Exception e) {
            request.setAttribute(ERR_MSG_ATTR, e.getMessage());
            try (PrintWriter out = response.getWriter()) {
                e.printStackTrace(out);
            }
        }

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
