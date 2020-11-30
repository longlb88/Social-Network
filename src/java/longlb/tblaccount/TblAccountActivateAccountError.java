/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblaccount;

import java.io.Serializable;

/**
 *
 * @author Long Le
 */
public class TblAccountActivateAccountError implements Serializable{
    private String wrongCodeError;

    /**
     * @return the wrongCodeError
     */
    public String getWrongCodeError() {
	return wrongCodeError;
    }

    /**
     * @param wrongCodeError the wrongCodeError to set
     */
    public void setWrongCodeError(String wrongCodeError) {
	this.wrongCodeError = wrongCodeError;
    }
    
    
    
}
