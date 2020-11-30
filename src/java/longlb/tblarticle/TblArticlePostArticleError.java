/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblarticle;

import java.io.Serializable;

/**
 *
 * @author Long Le
 */
public class TblArticlePostArticleError implements Serializable{
    private String TitleLengthError;
    private String DescriptionLengthError;

    /**
     * @return the TitleLengthError
     */
    public String getTitleLengthError() {
	return TitleLengthError;
    }

    /**
     * @param TitleLengthError the TitleLengthError to set
     */
    public void setTitleLengthError(String TitleLengthError) {
	this.TitleLengthError = TitleLengthError;
    }

    /**
     * @return the DescriptionLengthError
     */
    public String getDescriptionLengthError() {
	return DescriptionLengthError;
    }

    /**
     * @param DescriptionLengthError the DescriptionLengthError to set
     */
    public void setDescriptionLengthError(String DescriptionLengthError) {
	this.DescriptionLengthError = DescriptionLengthError;
    }
    
    
}
