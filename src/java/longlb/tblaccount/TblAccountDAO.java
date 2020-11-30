/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblaccount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longlb.utils.DBHelper;

/**
 *
 * @author Long Le
 */
public class TblAccountDAO implements Serializable{
    public boolean createAccount (String email, String name, 
	    String hashedPassword, String statusId) 
	    throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Get Connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "INSERT INTO tblAccount(email,name,password,statusId) "
			+ "VALUES(?,?,?,?)";
		stm = con.prepareStatement(sql);
		
		//3. Create query string
		stm.setString(1, email);
		stm.setString(2, name);
		stm.setString(3, hashedPassword);
		stm.setString(4, statusId);
		
		//4. Query
		int row = stm.executeUpdate();
		
		//5. Process data
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
    
    public TblAccountDTO checkLogin (String email, String hashedPassword) 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. Make connection
	    con = DBHelper.makeConnection();
	    if (con != null){
		//2. Create sql string
		String sql = "SELECT name, statusId "
			+ "FROM tblAccount "
			+ "WHERE email=? AND password=?";
		
		//3. Create query string
		stm = con.prepareStatement(sql);
		stm.setString(1, email);
		stm.setString(2, hashedPassword);
		
		//4. Query
		rs = stm.executeQuery();
		
		if (rs.next()){
		    String name = rs.getString("name");
		    String statusId = rs.getString("statusId");
		    
		    TblAccountDTO dto = new TblAccountDTO(email, name, hashedPassword, statusId);
		    return dto;
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
    
    public boolean activateAccount (String email) throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Get Connection
	    con = DBHelper.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "UPDATE tblAccount "
			+ "SET statusId = N'A' "
			+ "WHERE email = ?";
		stm = con.prepareStatement(sql);
		
		//3. Create query string
		stm.setString(1, email);
		
		//4. Query
		int row = stm.executeUpdate();
		
		//5. Process data
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
}
