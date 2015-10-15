package edu.wctc.drn.bookwebapp.controller;

import edu.wctc.drn.bookwebapp.entity.Author;
import edu.wctc.drn.bookwebapp.service.AuthorFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The main controller for author-related activities
 *
 * @author jlombardo
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/author"})
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!
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
    @Inject
    private AuthorFacade authorService;
    
    @Override
    public void init() throws ServletException {
        context = this.getServletContext();
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
        
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        try {
            
            Author author;
            List<Author> authors;
            String sId;
            String name;
            String sDate;
            Date dateAdded;
            Date timestamp;
            
            switch (action) {
                
                case LIST_ACTION:
                    authors = authorService.findAll();
                    request.setAttribute(AUTHORS_ATTR, authors);
                    destination = LIST_PAGE;
                    break;
                    
                case ADD_ACTION:
                    name = request.getParameter(NAME_PARAM);
                    if (name != null) {
                        author = new Author();
                        author.setAuthorName(name);
                        authorService.create(author);
                        timestamp = new Date();
                        request.setAttribute(TIMESTAMP_ATTR, timestamp);
                        request.setAttribute(AUTHOR_ATTR, author);
                    }
                    destination = ADD_PAGE;
                    break;
                    
                case EDIT_ACTION:
                    sId = request.getParameter(ID_PARAM);
                    int id = Integer.parseInt(sId);
                    name = request.getParameter(NAME_PARAM);
                    sDate = request.getParameter(DATE_ADDED_PARAM);
                    if (name == null || sDate == null) {
                        author = authorService.find(id);   
                    } else {
                        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
                        dateAdded = df.parse(sDate);
                        author = authorService.find(id);
                        author.setAuthorName(name);
                        author.setDateAdded(dateAdded);
                        authorService.edit(author);
                        timestamp = new Date();
                        Date d = new Date();
                        request.setAttribute(UPDATED_ATTR, true);
                        request.setAttribute(TIMESTAMP_ATTR, timestamp);
                    }
                    request.setAttribute(AUTHOR_ATTR, author);
                    destination = EDIT_PAGE;
                    break;
                        
                case DELETE_ACTION:
                    String[] ids = request.getParameterValues(ID_PARAM);
                    for (String s : ids) {
                        id = Integer.parseInt(s);
                        author = authorService.find(id);
                        authorService.remove(author);
                    }
                    authors = authorService.findAll();
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
