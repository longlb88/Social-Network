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
import longlb.article.ArticleObject;
import longlb.tblarticle.TblArticleDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Long Le
 */
@WebServlet(name = "SearchArticleServlet", urlPatterns = {"/SearchArticleServlet"})
public class SearchArticleServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";
    private Logger log = Logger.getLogger(SearchArticleServlet.class.getName());

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
	String searchValue = request.getParameter("txtSearch");
	
	int page = 1; //default page is 1
	String pageString = request.getParameter("page");
	if (pageString != null){
	    page = Integer.parseInt(pageString);
	}
	String url = HOME_PAGE;
	int articlePerPage = 20;

	try {
	    if (searchValue.length() > 0) {
		TblArticleDAO dao = new TblArticleDAO();
		dao.searchArticlesByContent(searchValue, page, articlePerPage);

		//get article list
		List<ArticleObject> articleList = dao.getArticleList();
		request.setAttribute("ARTICLE_LIST", articleList);

		//calculate total pages for paging
		int totalArticles = dao.getTotalResult(searchValue);
		int totalPage = calculateTotalPage(totalArticles, articlePerPage);
		request.setAttribute("TOTAL_PAGES", totalPage);

	    }//end if searchValue is not empty
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

    /**
     * Calculates total page need
     *
     * @param totalArticles total articles base on search value
     * @param articlesPerPage number of articles in 1 page
     * @return number of total page
     */
    private int calculateTotalPage(int totalArticles, int articlesPerPage) {
	int totalPage = 0;

	if (totalArticles % articlesPerPage != 0) {
	    totalPage = totalArticles / articlesPerPage + 1;
	} else {
	    totalPage = totalArticles / articlesPerPage;
	}

	return totalPage;
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
