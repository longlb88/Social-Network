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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longlb.tblarticle.TblArticleDAO;
import longlb.tblcomment.TblCommentDAO;
import longlb.tblemotion.TblEmotionDAO;
import longlb.tblnotification.TblNotificationDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "DeleteArticleServlet", urlPatterns = {"/DeleteArticleServlet"})
public class DeleteArticleServlet extends HttpServlet {
    private Logger log = Logger.getLogger(DeleteArticleServlet.class.getName());
    private final String HOME_PAGE = "home.jsp";
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
	String articleIdString = request.getParameter("articleId");
	int articleId = 0;
	if (articleIdString != null){
	    articleId = Integer.parseInt(articleIdString);
	}
	
	String url = HOME_PAGE;
	try {
	    if (articleId != 0){
		TblArticleDAO articleDAO = new TblArticleDAO();
		articleDAO.deleteArticle(articleId);

		//if user delete from home page, it will have search value on request
		String lastSearchValue = request.getParameter("txtSearch");
		if (lastSearchValue != null){
		    url = "DispatchController"
			    + "?btAction=Search"
			    + "&txtSearch=" + lastSearchValue;
		}

		//after delete article, delete comments, emotions and notification
		//delete comments
		TblCommentDAO commentDAO = new TblCommentDAO();
		commentDAO.deleteCommentsOfArticle(articleId);
		
		//delete emotions
		TblEmotionDAO emotionDAO = new TblEmotionDAO();
		emotionDAO.deleteEmotionsOfArticle(articleId);
		
		//delete notification
		TblNotificationDAO notificationDAO = new TblNotificationDAO();
		notificationDAO.deleteNotificationOfArticle(articleId);
	    }
	} catch (SQLException ex) {
	    log.error("SQLException: " + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException: " + ex.getMessage());
	} finally {
	    response.sendRedirect(url);
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
