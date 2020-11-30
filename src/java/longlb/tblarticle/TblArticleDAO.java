/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblarticle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import longlb.article.ArticleObject;
import longlb.utils.DBHelper;

/**
 *
 * @author Long Le
 */
public class TblArticleDAO implements Serializable{
    private final String ACTIVE_STATUS = "A";
    private final String DELETE_STATUS = "D";
    private List<ArticleObject> articleList;

    public List<ArticleObject> getArticleList() {
	return articleList;
    }

    public void searchArticlesByContent(String content, int page, int articlePerPage) throws NamingException, SQLException, IOException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "SELECT articleId, posterEmail, title, description, image, date, name "
			+ "FROM tblArticle,tblAccount "
			+ "WHERE tblArticle.posterEmail = tblAccount.email "
			+ "AND tblArticle.statusId = ? "
			+ "AND description LIKE ? "
			+ "ORDER BY date DESC "
			+ "OFFSET ? ROWS "
			+ "FETCH NEXT ? ROWS ONLY";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, ACTIVE_STATUS);
		stm.setString(2, "%" + content + "%");
		stm.setInt(3, (page - 1) * articlePerPage);
		stm.setInt(4, articlePerPage);
		
		//4. Query
		rs = stm.executeQuery();
		
		//5. Process data
		while (rs.next()){
		    int articleId = rs.getInt("articleId");
		    String postedUser = rs.getString("posterEmail");
		    String title = rs.getString("title");
		    String description = rs.getString("description");
		    Timestamp date = rs.getTimestamp("date");
		    String userName = rs.getString("name");
		    
		    //get image and encode to base64 string
		    String base64Image = null;
		    InputStream inputStream = rs.getBinaryStream("image");
		    if (inputStream != null){
			base64Image = getEncodeBase64Image(inputStream);
			inputStream.close();
		    }
		    
		    TblArticleDTO dto = new TblArticleDTO(articleId, postedUser, title, description, base64Image, date, ACTIVE_STATUS);
		    ArticleObject article = new ArticleObject(dto, userName);
		    if (articleList == null){
			articleList = new ArrayList<>();
		    }
		    articleList.add(article);
		}
	    }
	} finally {
	    if (rs != null){
		rs.close();
	    }
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
    }
    
    public int getTotalResult(String content) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try{
	     //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "SELECT COUNT(articleId) AS total "
			+ "FROM tblArticle "
			+ "WHERE statusId = ? "
			+ "AND description LIKE ? ";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, ACTIVE_STATUS);
		stm.setString(2, "%" + content + "%");
		
		//4. Query
		rs = stm.executeQuery();
		
		if (rs.next()){
		    int result = rs.getInt("total");
		    return result;
		}
	    }
	} finally {
	    if (rs != null){
		rs.close();
	    }
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return 0;
    }
    
    public ArticleObject getArticle(int articleId) throws SQLException, NamingException, IOException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "SELECT posterEmail, title, description, image, date, name "
			+ "FROM tblArticle, tblAccount "
			+ "WHERE tblArticle.posterEmail = tblAccount.email "
			+ "AND articleId = ? "
			+ "AND tblArticle.statusId = ?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, ACTIVE_STATUS);
		
		//4. Query 
		rs = stm.executeQuery();
		
		//5. Process data
		if (rs.next()){
		    String posterEmail = rs.getString("posterEmail");
		    String title = rs.getString("title");
		    String description = rs.getString("description");
		    Timestamp date = rs.getTimestamp("date");
		    String userName = rs.getString("name");
		    
		    //get image and encode to base64 string
		    String base64Image = null;
		    InputStream inputStream = rs.getBinaryStream("image");
		    if (inputStream != null){
			base64Image = getEncodeBase64Image(inputStream);
			inputStream.close();
		    }
		    
		    TblArticleDTO dto = new TblArticleDTO(articleId, posterEmail, title, description, base64Image, date, ACTIVE_STATUS);
		    ArticleObject article = new ArticleObject(dto, userName);
		    return article;
		}
	    }
	} finally {
	    if (rs != null){
		rs.close();
	    }
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return null;
    }
    public String getPosterEmail(int articleId) throws SQLException, NamingException, IOException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "SELECT posterEmail "
			+ "FROM tblArticle "
			+ "WHERE articleId = ? AND statusId = ?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, ACTIVE_STATUS);
		
		//4. Query 
		rs = stm.executeQuery();
		
		//5. Process data
		if (rs.next()){
		    String posterEmail = rs.getString("posterEmail");
		    
		    return posterEmail;
		}
	    }
	} finally {
	    if (rs != null){
		rs.close();
	    }
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return null;
    }
    
    public void createArticle (String posterEmail, String title, String description, Part imagePart) 
	    throws SQLException, NamingException, IOException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try{
	     //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "INSERT INTO TblArticle(posterEmail, title, description, image) "
			+ "VALUES(?,?,?,?)";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, posterEmail);
		stm.setString(2, title);
		stm.setString(3, description);
		stm.setBinaryStream(4, imagePart.getInputStream(), (int) imagePart.getSize());
		
		//4. Query
		stm.executeUpdate();	
	    }
	} finally {
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
    }
    
    public boolean deleteArticle (int articleId) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try{
	     //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "UPDATE tblArticle "
			+ "SET statusId = ? "
			+ "WHERE articleId = ?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, DELETE_STATUS);
		stm.setInt(2, articleId);
		
		//4. Query
		int row = stm.executeUpdate();
		
		if (row > 0){	
		    return true;
		}
	    }
	} finally {
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return false;
    }
    
    /**
     * get encode base64 string of image from binary input stream
     * @param inputStream binary input stream of image
     * @return image base64 String
     * @throws IOException 
     */
    private String getEncodeBase64Image(InputStream inputStream) throws IOException{
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	byte[] buffer = new byte[4096]; //limit each time read inpuStream
	int readByte;
		
	while ((readByte = inputStream.read(buffer)) != -1){ //read inputStream to buffer
	    outputStream.write(buffer, 0, readByte); //read buffer to outputStream
	}
	byte[] imageBytes = outputStream.toByteArray();
	return Base64.getEncoder().encodeToString(imageBytes);
    }
}
