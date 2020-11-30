/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblcomment;

import java.io.Serializable;

/**
 *
 * @author Long Le
 */
public class TblCommentPostCommentError implements Serializable{
    private String commentLengthError;

    /**
     * @return the commentLengthError
     */
    public String getCommentLengthError() {
	return commentLengthError;
    }

    /**
     * @param commentLengthError the commentLengthError to set
     */
    public void setCommentLengthError(String commentLengthError) {
	this.commentLengthError = commentLengthError;
    }
}
