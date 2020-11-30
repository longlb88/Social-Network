/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblcomment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longlb.comment.CommentObject;
import longlb.utils.DBHelper;

/**
 *
 * @author Long Le
 */
public class TblCommentDAO implements Serializable{
    private final String ACTIVE_STATUS = "A";
    private final String DELETE_STATUS = "D";

    private List<CommentObject> commentList;

    public List<CommentObject> getCommentList() {
	return commentList;
    }
    
    public void createComment (int articleId, String email, String comment) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "INSERT INTO tblComment(articleId, commentorEmail, comment) "
			+ "VALUES(?,?,?)";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, email);
		stm.setString(3, comment);
		
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
    
    public void getCommentsOfArticle (int articleId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "SELECT commentId, articleId, commentorEmail, comment, date, name "
			+ "FROM tblComment, tblAccount "
			+ "WHERE tblComment.commentorEmail = tblAccount.email "
			+ "AND articleId = ? AND tblComment.statusId = ? "
			+ "ORDER BY date DESC";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, ACTIVE_STATUS);
		
		//4. Query
		rs = stm.executeQuery();
		
		//5. Process data
		while (rs.next()){
		    int commentId = rs.getInt("commentId");
		    String commentorEmail = rs.getString("commentorEmail");
		    String comment = rs.getString("comment");
		    Timestamp date = rs.getTimestamp("date");
		    String userName = rs.getString("name");
		    
		    TblCommentDTO commentDTO = new TblCommentDTO(commentId, articleId, commentorEmail, comment, date, ACTIVE_STATUS);
		    CommentObject commentObject = new CommentObject(commentDTO, userName);
		    
		    if (commentList == null){
			commentList = new ArrayList<>();
		    }
		    
		    commentList.add(commentObject);
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
    
    public void deleteComment (int commentId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "UPDATE tblComment "
			+ "SET statusId=? "
			+ "WHERE commentId=?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, DELETE_STATUS);
		stm.setInt(2, commentId);
		
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
    
    public void deleteCommentsOfArticle (int articleId) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "UPDATE tblComment "
			+ "SET statusId=? "
			+ "WHERE articleId=?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, DELETE_STATUS);
		stm.setInt(2, articleId);
		
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
}
