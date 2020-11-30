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
import longlb.tblaccount.TblAccountActivateAccountError;
import longlb.tblaccount.TblAccountDAO;
import longlb.tblaccount.TblAccountDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "ActivateAccountServlet", urlPatterns = {"/ActivateAccountServlet"})
public class ActivateAccountServlet extends HttpServlet {

    private Logger log = Logger.getLogger(ActivateAccountServlet.class.getName());
    private final String ERROR_PAGE = "activate-account.jsp";
    private final String HOME_PAGE = "home.jsp";
    private final String ACTIVE_STATUS = "A";
    
    public ActivateAccountServlet() {
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
	PrintWriter out = response.getWriter();
	String generatedCode = request.getParameter("generatedCode");
	String activationCode = request.getParameter("txtActivationCode");

	TblAccountActivateAccountError error = null;
	String url = ERROR_PAGE;
	
	try {
	    if (!activationCode.equals(generatedCode)) {
		error = new TblAccountActivateAccountError();
		error.setWrongCodeError("The inputted code is wrong! Please try again or resend the activation code!");
		request.setAttribute("ACTIVATE_ERROR", error);
		request.setAttribute("ACTIVATION_CODE", generatedCode);
	    }

	    if (error == null) {
		//get user email
		HttpSession session = request.getSession(false);
		TblAccountDTO currentUser = (TblAccountDTO) session.getAttribute("LOGIN_ACCOUNT");
		String currentEmail = currentUser.getEmail();

		TblAccountDAO dao = new TblAccountDAO();
		boolean result = dao.activateAccount(currentEmail);
		if (result) {
		    currentUser.setStatusId(ACTIVE_STATUS);
		    session.setAttribute("LOGIN_ACCOUNT", currentUser);
		    url = HOME_PAGE;
		}
	    }
	} catch (NamingException ex) {
	    log.error("NamingException: " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException: " + ex.getMessage());
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
