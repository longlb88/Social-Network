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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longlb.tblaccount.TblAccountCreateUserErrors;
import longlb.tblaccount.TblAccountDAO;
import longlb.utils.PasswordHasher;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/CreateUserServlet"})
public class CreateUserServlet extends HttpServlet {

    private final String ERROR_PAGE = "register.jsp";
    private final String SUCCESS_PAGE = "account-created.html";
    private Logger log = Logger.getLogger(CreateUserServlet.class.getName());

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

	String email = request.getParameter("txtEmail");
	String name = request.getParameter("txtFullname");
	String password = request.getParameter("txtPassword");
	String confirm = request.getParameter("txtConfirm");

	TblAccountCreateUserErrors errors = new TblAccountCreateUserErrors();
	boolean foundErr = false;

	String url = ERROR_PAGE;

	try {
	    //check for user errors
	    //check wrong email format
	    if (!email.matches("(\\w*\\d*)+@(\\w+\\.\\w+){1}(\\.\\w+)?")){
		foundErr = true;
		errors.setEmailFormatError("Email format error!");
	    }
	    //check full name length
	    if (name.length() < 2 || name.length() > 50) {
		foundErr = true;
		errors.setFullNameLengthError("Full name must in range from 2 - 50 chars");
	    }

	    //check password length
	    if (password.length() < 6 || password.length() > 30) {
		foundErr = true;
		errors.setPasswordLengthError("Password must in range from 6 - 30 chars");
	    } else if (!confirm.equals(password)) {
		foundErr = true;
		errors.setConfirmNotMatched("Confirm not matched");
	    }

	    if (foundErr) {
		request.setAttribute("CREATE_ERRORS", errors);
	    } else {
		//hashed password
		String hashedPassword = PasswordHasher.hash(password);
		TblAccountDAO dao = new TblAccountDAO();

		//statusId = 1 mean "new"
		boolean result = dao.createAccount(email, name, hashedPassword, "N");
		
		if (result) {
		    url = SUCCESS_PAGE;
		}

	    }
	} catch (NoSuchAlgorithmException ex) {
	    log.error("NoSuchAlgorithmException: " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException: " + ex.getMessage());
	    
	    if (ex.getMessage().contains("duplicate")){
		errors.setEmailExisted(email + " EXISTED!");
		request.setAttribute("CREATE_ERRORS", errors);
	    }

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
