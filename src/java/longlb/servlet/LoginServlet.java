/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longlb.tblaccount.TblAccountDAO;
import longlb.tblaccount.TblAccountDTO;
import longlb.utils.PasswordHasher;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final String ERROR_PAGE = "invalid.html";
    private final String HOME_PAGE = "home.jsp";
    private final String NEED_ACTIVATE_PAGE = "activate-require.jsp";
    private final Logger log = Logger.getLogger(LoginServlet.class.getName());
    
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
	String url = ERROR_PAGE;
	String email = request.getParameter("txtEmail");
	String password = request.getParameter("txtPassword");
	
	try {
	    String hashedPassword = PasswordHasher.hash(password);
	    TblAccountDAO dao = new TblAccountDAO();
	    
	    TblAccountDTO loginAccount = dao.checkLogin(email, hashedPassword);
	    if (loginAccount != null){
		HttpSession session = request.getSession(true);
		session.setAttribute("LOGIN_ACCOUNT", loginAccount);
		
		//if user has activate the account
		if (loginAccount.getStatusId().equals("A")){
		    Cookie emailCookie = new Cookie("EMAIL", email);
		    emailCookie.setMaxAge(3*60);
		    response.addCookie(emailCookie);

		    Cookie passwordCookie = new Cookie("PASSWORD", hashedPassword);
		    passwordCookie.setMaxAge(3*60);
		    response.addCookie(passwordCookie);

		    url = HOME_PAGE;
		}
		else { //if user account isn't activated
		    url = NEED_ACTIVATE_PAGE;
		}
	    }
	    
	} catch (NoSuchAlgorithmException ex) {
	    log.error("NoSuchAlgorithmException :" + ex.getMessage());
	} catch (NamingException ex) {
	    log.error("NamingException :" + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException :" + ex.getMessage());
	} finally{
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
