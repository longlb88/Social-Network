/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longlb.article.ArticleObject;
import longlb.comment.CommentObject;
import longlb.tblaccount.TblAccountDTO;
import longlb.tblarticle.TblArticleDAO;
import longlb.tblcomment.TblCommentDAO;
import longlb.tblemotion.TblEmotionDAO;
import longlb.tblemotion.TblEmotionDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "ViewDetailArticleServlet", urlPatterns = {"/ViewDetailArticleServlet"})
public class ViewDetailArticleServlet extends HttpServlet {
    private final String DETAIL_PAGE = "article-detail.jsp";
    private Logger log = Logger.getLogger(ViewDetailArticleServlet.class.getName());
    private final String ACTIVE_STATUS = "A";
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
	String url = DETAIL_PAGE;
	String articleIdString = request.getParameter("articleId");
	int articleId = 0;
	if (articleIdString != null){
	    articleId = Integer.parseInt(articleIdString);
	}
	
	try {
	    //get current user
	    HttpSession session = request.getSession(false);
	    TblAccountDTO currentUser = (TblAccountDTO) session.getAttribute("LOGIN_ACCOUNT");
	    
	    //get article info
	    TblArticleDAO articleDAO = new TblArticleDAO();
	    ArticleObject article = articleDAO.getArticle(articleId);
	    request.setAttribute("ARTICLE", article);

	    //count emotions
	    TblEmotionDAO emotionDAO = new TblEmotionDAO();
	    int like = emotionDAO.countEmotion(articleId, "Like");
	    request.setAttribute("LIKE", like);
	    int dislike = emotionDAO.countEmotion(articleId, "Dislike");
	    request.setAttribute("DISLIKE", dislike);
	    
	    //get emotion status
	    TblEmotionDTO reactedEmotion = emotionDAO.getEmotionByUser(articleId, currentUser.getEmail());
	    if (reactedEmotion != null && reactedEmotion.getStatus().equals(ACTIVE_STATUS)){
		request.setAttribute("REACT_STATUS", "You " + reactedEmotion.getEmotion() + " this post!");
	    }
	    
	    //get comments
	    TblCommentDAO commentDAO = new TblCommentDAO();
	    commentDAO.getCommentsOfArticle(articleId);
	    List<CommentObject> listComment = commentDAO.getCommentList();
	    if (listComment != null){
		request.setAttribute("COMMENTS_LIST", listComment);
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
