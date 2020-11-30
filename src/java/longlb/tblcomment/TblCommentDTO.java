/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblcomment;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Long Le
 */
public class TblCommentDTO implements Serializable{
    private int commentId;
    private int articleId;
    private String commentorEmail;
    private String comment;
    private Timestamp date;
    private String statusId;

    public TblCommentDTO() {
    }

    public TblCommentDTO(int commentId, int articleId, String commentorEmail, String comment, Timestamp date, String statusId) {
	this.commentId = commentId;
	this.articleId = articleId;
	this.commentorEmail = commentorEmail;
	this.comment = comment;
	this.date = date;
	this.statusId = statusId;
    }
    
    /**
     * @return the articleId
     */
    public int getArticleId() {
	return articleId;
    }

    /**
     * @param articleId the articleId to set
     */
    public void setArticleId(int articleId) {
	this.articleId = articleId;
    }

    /**
     * @return the commentId
     */
    public int getCommentId() {
	return commentId;
    }

    /**
     * @param commentId the commentId to set
     */
    public void setCommentId(int commentId) {
	this.commentId = commentId;
    }

    /**
     * @return the commentorEmail
     */
    public String getCommentorEmail() {
	return commentorEmail;
    }

    /**
     * @param email the email to set
     */
    public void setCommentorEmail(String email) {
	this.commentorEmail = email;
    }

    /**
     * @return the comment
     */
    public String getComment() {
	return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
	this.comment = comment;
    }

    /**
     * @return the date
     */
    public Timestamp getDate() {
	return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
	this.date = date;
    }

    /**
     * @return the statusId
     */
    public String getStatusId() {
	return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(String statusId) {
	this.statusId = statusId;
    }
    
    
}
