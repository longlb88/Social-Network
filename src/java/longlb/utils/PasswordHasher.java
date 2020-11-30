/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashing password with SHA-256 algorithm
 * @author Long Le
 */
public class PasswordHasher {
    public static byte [] hashToBytes (String password) throws NoSuchAlgorithmException{
	MessageDigest digester = MessageDigest.getInstance("SHA-256");
	byte[] hashedPassword = digester.digest(password.getBytes(StandardCharsets.UTF_8));
	
	return hashedPassword;
    }
    
    public static String convertToHex (byte [] hashBytes){
	String hexString = new String();
	
	for (int i = 0; i < hashBytes.length; i++){
	    //0xFF means 32 bits, with all 0's excepts 8 lowest bits is 1
	    //using & to get only 8 lowest bits of hex string 
	    //due to a byte only has 8 bits
	    String hex = Integer.toHexString(0xFF & hashBytes[i]); 
	    if (hex.length() == 1){
		hexString += "0";
	    }
	    hexString += hex;
	}
	return hexString;
    } 
    
    public static String hash(String password) throws NoSuchAlgorithmException{
	byte[] hashedPassword = hashToBytes(password);
	String hexString = convertToHex(hashedPassword);
	
	return hexString;
    }
}
