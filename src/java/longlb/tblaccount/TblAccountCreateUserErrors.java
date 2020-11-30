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
public class TblAccountCreateUserErrors implements Serializable{
    private String emailFormatError;
    private String passwordLengthError;
    private String confirmNotMatched;
    private String fullNameLengthError;
    private String emailExisted;

    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
	return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
	this.passwordLengthError = passwordLengthError;
    }

    /**
     * @return the fullNameLengthError
     */
    public String getFullNameLengthError() {
	return fullNameLengthError;
    }

    /**
     * @param fullNameLengthError the fullNameLengthError to set
     */
    public void setFullNameLengthError(String fullNameLengthError) {
	this.fullNameLengthError = fullNameLengthError;
    }

    /**
     * @return the emailExisted
     */
    public String getEmailExisted() {
	return emailExisted;
    }

    /**
     * @param emailExisted the emailExisted to set
     */
    public void setEmailExisted(String emailExisted) {
	this.emailExisted = emailExisted;
    }

    /**
     * @return the confirmNotMatched
     */
    public String getConfirmNotMatched() {
	return confirmNotMatched;
    }

    /**
     * @param confirmNotMatched the confirmNotMatched to set
     */
    public void setConfirmNotMatched(String confirmNotMatched) {
	this.confirmNotMatched = confirmNotMatched;
    }

    /**
     * @return the emailFormatError
     */
    public String getEmailFormatError() {
	return emailFormatError;
    }

    /**
     * @param emailFormatError the emailFormatError to set
     */
    public void setEmailFormatError(String emailFormatError) {
	this.emailFormatError = emailFormatError;
    }
    
    
}
