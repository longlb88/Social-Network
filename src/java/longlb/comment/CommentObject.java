/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.comment;

import java.io.Serializable;
import longlb.tblcomment.TblCommentDTO;

/**
 *
 * @author Long Le
 */
public class CommentObject implements Serializable{
    private TblCommentDTO commentDTO;
    private String userName;

    public CommentObject() {
    }

    public CommentObject(TblCommentDTO commentDTO, String userName) {
	this.commentDTO = commentDTO;
	this.userName = userName;
    }

    /**
     * @return the commentDTO
     */
    public TblCommentDTO getCommentDTO() {
	return commentDTO;
    }

    /**
     * @param commentDTO the commentDTO to set
     */
    public void setCommentDTO(TblCommentDTO commentDTO) {
	this.commentDTO = commentDTO;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
	return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
	this.userName = userName;
    }
    
    
}
