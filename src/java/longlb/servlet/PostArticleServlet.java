/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import longlb.tblaccount.TblAccountDTO;
import longlb.tblarticle.TblArticleDAO;
import longlb.tblarticle.TblArticlePostArticleError;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "PostArticleServlet", urlPatterns = {"/PostArticleServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
       maxFileSize = 1024 * 1024 * 10, // 10MB
       maxRequestSize = 1024 * 1024 * 50) // 50MB
public class PostArticleServlet extends HttpServlet {
    private final String ERROR_PAGE = "home.jsp";
    private final Logger log = Logger.getLogger(PostArticleServlet.class.getName());
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
	PrintWriter out = response.getWriter();
	
	String title = request.getParameter("txtTitle");
	String description = request.getParameter("txtDescription");
	//get image part
	Part imagePart = request.getPart("image");
	String url = ERROR_PAGE;
	
	boolean foundErr= false;
	TblArticlePostArticleError error = new TblArticlePostArticleError();
	
	try {
	    //check for user error
	    //check title length
	    if (title.isEmpty() || title.length() > 200){
		foundErr = true;
		error.setTitleLengthError("Title can't be empty and length limit to 200 chars");
	    } 
	    //check description length
	    if (description.isEmpty() || description.length() > 2000){
		foundErr = true;
		error.setDescriptionLengthError("Description can't be empty and length limit to 2000 chars");
	    }
	    
	    if (foundErr){
		request.setAttribute("POST_ARTICLE_ERRORS", error);
	    } else {
		//get current user email
		HttpSession session = request.getSession(false);
		TblAccountDTO currentUser = (TblAccountDTO) session.getAttribute("LOGIN_ACCOUNT");
		String email = currentUser.getEmail();
		  
		//call DAO to create article
		TblArticleDAO articleDAO = new TblArticleDAO();
		articleDAO.createArticle(email, title, description, imagePart);
	    }
	    
	} catch (SQLException ex) {
	    log.error("SQLException: " + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException: " + ex.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	    out.close();
	}
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
