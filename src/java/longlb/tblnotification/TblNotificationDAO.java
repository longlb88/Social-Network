/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblnotification;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;
import longlb.utils.DBHelper;

/**
 *
 * @author Long Le
 */
public class TblNotificationDAO implements Serializable{
    private final String ACTIVE_STATUS = "A";
    private final String DELETE_STATUS = "D";
    private List<TblNotificationDTO> listNotifications;

    public List<TblNotificationDTO> getListNotifications() {
	return listNotifications;
    }
    
    public void loadNotifications(String email) throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. create sql string
		String sql = "SELECT notificationId, tblNotification.articleId, "
			+ "creatorEmail, content, tblNotification.date, typeId "
			+ "FROM tblNotification, tblArticle "
			+ "WHERE tblNotification.articleId = tblArticle.articleId "
			+ "AND tblArticle.posterEmail=? AND tblNotification.statusId = ? "
			+ "ORDER BY tblNotification.date DESC";
		
		//3. create query string
		stm = con.prepareStatement(sql);
		stm.setString(1, email);
		stm.setString(2, ACTIVE_STATUS);
		
		//4. query
		rs = stm.executeQuery();
		
		//5. Process data
		while(rs.next()){
		    int notificationId = rs.getInt("notificationId");
		    int articleId = rs.getInt("articleId");
		    String creatorEmail = rs.getString("creatorEmail");
		    String content = rs.getString("content");
		    Timestamp date = rs.getTimestamp("date");
		    String typeId = rs.getString("typeId");
		    
		    TblNotificationDTO dto = new TblNotificationDTO(notificationId, articleId, creatorEmail, content, date, ACTIVE_STATUS, typeId);
		    
		    if (listNotifications == null){
			listNotifications = new ArrayList<>();
		    }
		    
		    listNotifications.add(dto);
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
    
    public void createNotification(int articleId, String creatorEmail, String typeId, String content) 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. create sql string
		String sql = "INSERT INTO tblNotification(articleId, creatorEmail, typeId, content) "
			+ "VALUES(?, ?, ?, ?)";
		
		//3. create query string
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, creatorEmail);
		stm.setString(3, typeId);
		stm.setString(4, content);
		
		//4. query
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
    
    public int checkExistedNotification(int articleId, String typeId) 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. create sql string
		String sql = "SELECT notificationId "
			+ "FROM tblNotification "
			+ "WHERE articleId=? AND typeId=? AND statusId = ?";
		
		//3. create query string
		stm = con.prepareStatement(sql);
		stm.setInt(1, articleId);
		stm.setString(2, typeId);
		stm.setString(3, ACTIVE_STATUS);
		
		//4. query
		rs = stm.executeQuery();
		
		//5. Process data
		if (rs.next()){
		    int notificationId = rs.getInt("notificationId");
		    return notificationId;
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
    
    public void updateNotification(int notificationId, String creatorEmail, String content) 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. create sql string
		String sql = "UPDATE tblNotification "
			+ "SET creatorEmail=?,content=?,date=? "
			+ "WHERE notificationId=?";
		
		//3. create query string
		stm = con.prepareStatement(sql);
		stm.setString(1, creatorEmail);
		stm.setString(2, content);
		
		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		stm.setTimestamp(3, timestamp);
		
		stm.setInt(4, notificationId);
		
		//4. query
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
    
    public void deleteNotificationOfArticle(int articleId) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "UPDATE tblNotification "
			+ "SET statusId = ? "
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
