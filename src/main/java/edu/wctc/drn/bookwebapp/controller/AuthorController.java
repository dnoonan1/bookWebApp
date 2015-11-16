package edu.wctc.drn.bookwebapp.controller;

import edu.wctc.drn.bookwebapp.entity.Author;
import edu.wctc.drn.bookwebapp.service.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for author-related activities
 *
 * @author dnoonan1
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

    private static final String ACTION_LIST = "list";
    private static final String ACTION_ADD = "add";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_VIEW_STATS = "viewStats";
    private static final String ACTION_AJAX_LIST = "listAjax";
    private static final String ACTION_AJAX_FIND_BY_ID = "findByIdAjax";

    private static final String PARAM_ACTION = "action";
    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DATE_ADDED = "dateAdded";

    private static final String ATTR_AUTHOR = "author";
    private static final String ATTR_AUTHORS = "authors";
    private static final String ATTR_TIMESTAMP = "timestamp";
    private static final String ATTR_UPDATED = "updated";
    private static final String ATTR_STATS = "stats";
    private static final String ERR_MSG_ATTR = "errMsg";

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";

    private ServletContext appContext;
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
        PrintWriter out = response.getWriter();
        
        if (authorService == null) {
            initAuthorService();
        }
        
        String destination = LIST_PAGE;
        String action = request.getParameter(PARAM_ACTION);
        
        try {
            
            Author author;
            List<Author> authors;
            String sId;
            String name;
            String sDate;
            Date dateAdded;
            Date timestamp;
            
            switch (action) {
                
                case ACTION_AJAX_LIST:
                    authors = authorService.findAll();
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    
                    authors.forEach((a) -> {
                        jsonArrayBuilder.add(
                            Json.createObjectBuilder()
                                    .add(PARAM_ID, a.getAuthorId())
                                    .add(PARAM_NAME, a.getAuthorName())
                                    .add(PARAM_DATE_ADDED, a.getDateAdded().toString())
                        );
                    });
                    
                    response.setContentType("application/json");
                    out.write(jsonArrayBuilder.build().toString());
                    out.flush();
                    return; // prevent request dispatcher!
                
                case ACTION_AJAX_FIND_BY_ID:
                    // TODO
                    return;
                
                case ACTION_LIST:
                    authors = authorService.findAll();
                    request.setAttribute(ATTR_AUTHORS, authors);
                    destination = LIST_PAGE;
                    break;
                    
                case ACTION_ADD:
                    name = request.getParameter(PARAM_NAME);
                    if (name != null) {
                        author = new Author();
                        author.setAuthorName(name);
                        authorService.edit(author);
                        timestamp = new Date();
                        request.setAttribute(ATTR_TIMESTAMP, timestamp);
                        request.setAttribute(ATTR_AUTHOR, author);
                    }
                    destination = ADD_PAGE;
                    break;
                    
                case ACTION_EDIT:
                    sId = request.getParameter(PARAM_ID);
                    int id = Integer.parseInt(sId);
                    name = request.getParameter(PARAM_NAME);
                    sDate = request.getParameter(PARAM_DATE_ADDED);
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
                        request.setAttribute(ATTR_UPDATED, true);
                        request.setAttribute(ATTR_TIMESTAMP, timestamp);
                    }
                    request.setAttribute(ATTR_AUTHOR, author);
                    destination = EDIT_PAGE;
                    break;
                        
                case ACTION_DELETE:
                    String[] ids = request.getParameterValues(PARAM_ID);
                    for (String s : ids) {
                        id = Integer.parseInt(s);
                        author = authorService.find(id);
                        authorService.remove(author);
                    }
                    authors = authorService.findAll();
                    request.setAttribute(ATTR_AUTHORS, authors);
                    destination = LIST_PAGE;
                    break;
                
                case ACTION_VIEW_STATS:
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
