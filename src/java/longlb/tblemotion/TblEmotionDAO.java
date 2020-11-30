/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblemotion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.naming.NamingException;
import longlb.utils.DBHelper;

/**
 *
 * @author Long Le
 */
public class TblEmotionDAO implements Serializable{
    private final String ACTIVE_STATUS = "A";
    public int countEmotion(int articleId, String emotion) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "SELECT COUNT(emotionId) AS total "
			+ "FROM tblEmotion "
			+ "WHERE articleId =? "
			+ "AND emotion = ? "
			+ "AND statusId = ?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, emotion);
		stm.setString(3, ACTIVE_STATUS);
		
		//4. Query
		rs = stm.executeQuery();
		
		//5. Process data
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
    
    public TblEmotionDTO getEmotionByUser(int articleId, String email) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "SELECT emotionId, emotion, statusId, date "
			+ "FROM tblEmotion "
			+ "WHERE articleId =? "
			+ "AND reactorEmail = ?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, email);
		
		//4. Query
		rs = stm.executeQuery();
		
		//5. Process data
		if (rs.next()){
		    int emotionId = rs.getInt("emotionId");
		    String emotion = rs.getString("emotion");
		    String status = rs.getString("statusId");
		    Timestamp date = rs.getTimestamp("date");
		    
		    TblEmotionDTO result = new TblEmotionDTO(emotionId, articleId, email, emotion, status, date);
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
	return null;
    }
    
    public void createEmotion(int articleId, String emotion, String currentUserEmail) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "INSERT INTO tblEmotion(articleId, reactorEmail, emotion) "
			+ "VALUES(?,?,?)";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, currentUserEmail);
		stm.setString(3, emotion);
		
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
    
    public void updateEmotion(int emotionId, String emotion, String status) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql statement
		String sql = "UPDATE tblEmotion "
			+ "SET statusId = ?, emotion = ?, date = ? "
			+ "WHERE emotionId = ?";
		
		//3.Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, status);
		stm.setString(2, emotion);
		
		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		stm.setTimestamp(3, timestamp);
		
		stm.setInt(4, emotionId);
		
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
    
    public void deleteEmotionsOfArticle(int articleId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "UPDATE tblEmotion "
			+ "SET statusId=N'D' "
			+ "WHERE articleId=?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		
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
