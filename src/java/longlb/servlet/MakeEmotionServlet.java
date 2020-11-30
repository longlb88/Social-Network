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
import javax.servlet.http.HttpSession;
import longlb.tblaccount.TblAccountDTO;
import longlb.tblarticle.TblArticleDAO;
import longlb.tblemotion.TblEmotionDAO;
import longlb.tblemotion.TblEmotionDTO;
import longlb.utils.NotificationSender;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "MakeEmotionServlet", urlPatterns = {"/MakeEmotionServlet"})
public class MakeEmotionServlet extends HttpServlet {

    private Logger log = Logger.getLogger(MakeEmotionServlet.class.getName());
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
	String emotion = request.getParameter("btAction");
	String articleIdString = request.getParameter("articleId");
	int articleId = 0 ;
	if (articleIdString != null){
	    articleId = Integer.parseInt(articleIdString);
	}

	try {
	    //get current user email
	    HttpSession session = request.getSession(false);
	    TblAccountDTO currentUser = (TblAccountDTO) session.getAttribute("LOGIN_ACCOUNT");
	    String currentUserEmail = currentUser.getEmail();

	    //get current user emotion
	    TblEmotionDAO dao = new TblEmotionDAO();
	    TblEmotionDTO reactedEmotion = dao.getEmotionByUser(articleId, currentUserEmail);

	    if (reactedEmotion == null) {
		//if user haven't react create emotion for the post
		dao.createEmotion(articleId, emotion, currentUserEmail);
	    } else {
		if (reactedEmotion.getStatus().equals("A")) {
		    //if emotion is hasn't been deleted
		    if (reactedEmotion.getEmotion().equals(emotion)) {
			//if same emotion delete emotion & notification
			dao.updateEmotion(reactedEmotion.getEmotionId(), reactedEmotion.getEmotion(), "D");
		    } else {
			//if different emotion change emotion type
			dao.updateEmotion(reactedEmotion.getEmotionId(), emotion, "A");
		    }
		} else {
		    //if emotion has been deleted, restore and change emotion
		    dao.updateEmotion(reactedEmotion.getEmotionId(), emotion, "A");
		}
	    }
	    
	    //send notification if others except post owner react on post
	    TblArticleDAO articleDAO = new TblArticleDAO();
	    String articlePosterEmail = articleDAO.getPosterEmail(articleId);
	    if (!articlePosterEmail.equals(currentUserEmail)){
		String notificationType = "R"; //R is for reaction
		NotificationSender.sendNotification(articleId, currentUserEmail, emotion, notificationType);
	    }
	} catch (SQLException ex) {
	    log.error("SQLException: " + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException: " + ex.getMessage());
	} finally {
	    String page = request.getParameter("page");
	    String lastSearchValue = request.getParameter("txtSearch");
	    String urlRewriting = "DispatchController?btAction=View Detail"
		    + "&articleId=" + articleId 
		    + "&txtSearch=" + lastSearchValue
		    + "&page=" + page;
	    response.sendRedirect(urlRewriting);
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
