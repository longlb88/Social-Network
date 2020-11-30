/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblnotification;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Long Le
 */
public class TblNotificationDTO implements Serializable{
    private int notificationId;
    private int articleId;
    private String creatorEmail;
    private String content;
    private Timestamp date;
    private String statusId;
    private String typeId;

    public TblNotificationDTO() {
    }

    public TblNotificationDTO(int notificationId, int articleId, String creatorEmail, String content, Timestamp date, String statusId, String typeId) {
	this.notificationId = notificationId;
	this.articleId = articleId;
	this.creatorEmail = creatorEmail;
	this.content = content;
	this.date = date;
	this.statusId = statusId;
	this.typeId = typeId;
    }

    /**
     * @return the notificationId
     */
    public int getNotificationId() {
	return notificationId;
    }

    /**
     * @param notificationId the notificationId to set
     */
    public void setNotificationId(int notificationId) {
	this.notificationId = notificationId;
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
     * @return the creatorEmail
     */
    public String getCreatorEmail() {
	return creatorEmail;
    }

    /**
     * @param creatorEmail the creatorEmail to set
     */
    public void setCreatorEmail(String creatorEmail) {
	this.creatorEmail = creatorEmail;
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

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }       

    /**
     * @return the typeId
     */
    public String getTypeId() {
	return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(String typeId) {
	this.typeId = typeId;
    }
}
