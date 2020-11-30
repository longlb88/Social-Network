/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longlb.tblaccount.TblAccountDTO;
import longlb.utils.MailHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "SendActivationCodeServlet", urlPatterns = {"/SendActivationCodeServlet"})
public class SendActivationCodeServlet extends HttpServlet {
    private Logger log = Logger.getLogger(SendActivationCodeServlet.class.getName());
    private final String CONFIRM_PAGE = "activate-account.jsp";
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
	String url = CONFIRM_PAGE;
	
	try {
	    //get current user email
	    HttpSession session = request.getSession(false);
	    if (session != null){
		TblAccountDTO currentUser = (TblAccountDTO) session.getAttribute("LOGIN_ACCOUNT");
		String currentEmail = currentUser.getEmail();
		
		//generate random code with 6 digit
		String activationCode = generateRandomCode(6);
		
		//set to request scope to use for confirmation
		request.setAttribute("ACTIVATION_CODE", activationCode);
		
		//send activation email
		MailHelper.sendAccountActivationCode(activationCode, currentEmail);
	    }
	} catch (MessagingException ex) {
	    log.error("MessagingException: " + ex.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	    out.close();
	}
    }
    
    private String generateRandomCode(int codeLength){
	Random rand = new Random();
	String code = "";
	
	for (int i = 0; i < codeLength; i++){
	    code += rand.nextInt(10);
	}
	
	return code;
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
