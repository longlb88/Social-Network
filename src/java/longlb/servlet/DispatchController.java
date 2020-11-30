	/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Long Le
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
       maxFileSize = 1024 * 1024 * 10, // 10MB
       maxRequestSize = 1024 * 1024 * 50) // 50MB
public class DispatchController extends HttpServlet {

    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String CREATE_ACCOUNT_CONTROLLER = "CreateUserServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String STARTUP_CONTROLLER = "StartupServlet";
    private final String SEARCH_ARTICLE_CONTROLLER = "SearchArticleServlet";
    private final String VIEW_ARTICLE_CONTROLLER = "ViewDetailArticleServlet";
    private final String MAKE_EMOTION_CONTROLLER = "MakeEmotionServlet";
    private final String POST_ARTICLE_CONTROLLER = "PostArticleServlet";
    private final String DELETE_ARTICLE_CONTROLLER = "DeleteArticleServlet";
    private final String ARTICLE_COMMENT_CONTROLLER = "PostCommentServlet";
    private final String DELETE_COMMENT_CONTROLLER = "DeleteCommentServlet";
    private final String VIEW_NOTIFICATIONS_CONTROLLER = "ViewNotificationsServlet";
    private final String SEND_ACTIVATION_CODE_CONTROLLER = "SendActivationCodeServlet";
    private final String ACTIVATE_ACCOUNT_CONTROLLER = "ActivateAccountServlet";
    
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
	String button = request.getParameter("btAction");
	String url = STARTUP_CONTROLLER;
	
	try {
	    if (button != null){
		if (button.equals("Login")){
		    url = LOGIN_CONTROLLER;
		} else if (button.equals("Create Account")){
		    url = CREATE_ACCOUNT_CONTROLLER;
		} else if (button.equals("Logout")){
		    url = LOGOUT_CONTROLLER;
		} else if (button.equals("Search")){
		    url = SEARCH_ARTICLE_CONTROLLER;
		} else if (button.equals("View Detail")){
		    url = VIEW_ARTICLE_CONTROLLER;
		} else if (button.equals("Like") || button.equals("Dislike")){
		    url = MAKE_EMOTION_CONTROLLER;
		} else if (button.equals("Post Article")){
		    url = POST_ARTICLE_CONTROLLER;
		} else if (button.equals("Delete Article")){
		    url = DELETE_ARTICLE_CONTROLLER;
		} else if (button.equals("Comment")){
		    url = ARTICLE_COMMENT_CONTROLLER;
		} else if (button.equals("Delete Comment")){
		    url = DELETE_COMMENT_CONTROLLER;
		} else if (button.equals("Notifications")){
		    url = VIEW_NOTIFICATIONS_CONTROLLER;
		} else if (button.equals("Send Activation")){
		    url = SEND_ACTIVATION_CODE_CONTROLLER;
		} else if (button.equals("Activate Account")){
		    url = ACTIVATE_ACCOUNT_CONTROLLER;
		}
	    }//end if button is exist
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
