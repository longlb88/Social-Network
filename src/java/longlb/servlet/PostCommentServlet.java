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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longlb.tblaccount.TblAccountDTO;
import longlb.tblarticle.TblArticleDAO;
import longlb.tblcomment.TblCommentDAO;
import longlb.tblcomment.TblCommentPostCommentError;
import longlb.utils.NotificationSender;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "PostCommentServlet", urlPatterns = {"/PostCommentServlet"})
public class PostCommentServlet extends HttpServlet {

    private Logger log = Logger.getLogger(PostCommentServlet.class.getName());

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
	String comment = request.getParameter("txtComment");
	String articleIdString = request.getParameter("articleId");
	int articleId = 0;
	if (articleIdString != null){
	    articleId = Integer.parseInt(articleIdString);
	}
	
	boolean foundErr = false;
	TblCommentPostCommentError error = new TblCommentPostCommentError();

	try {
	    //check comment length error
	    if (comment.isEmpty() || comment.length() > 200) {
		foundErr = true;
		error.setCommentLengthError("Comment can't be empty and limit to 200 chars");
	    }

	    if (foundErr) {
		request.setAttribute("COMMENT_ERROR", error);
	    } else {
		//get current user email
		HttpSession session = request.getSession(false);
		TblAccountDTO currAccount = (TblAccountDTO) session.getAttribute("LOGIN_ACCOUNT");
		String currentUserEmail = currAccount.getEmail();

		//create comment
		TblCommentDAO commentDAO = new TblCommentDAO();
		commentDAO.createComment(articleId, currentUserEmail, comment);
		
		//send notification if others except post owner comment on post
		TblArticleDAO articleDAO = new TblArticleDAO();
		String articlePosterEmail = articleDAO.getPosterEmail(articleId);
		if (!articlePosterEmail.equals(currentUserEmail)){
		    String notificationType = "C"; //C is for reaction
		    NotificationSender.sendNotification(articleId, currentUserEmail, comment, notificationType);
		}
	    }
	} catch (SQLException ex) {
	    log.error("SQLException: " + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException: " + ex.getMessage());
	} finally {
	    String url = "DispatchController?btAction=View Detail"
		    + "&articleId=" + articleId;
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
