/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblarticle;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Long Le
 */
public class TblArticleDTO implements Serializable{
    private int articleId;
    private String posterEmail;
    private String title;
    private String description;
    private String image;
    private Timestamp date;
    private String status;

    public TblArticleDTO() {
    }

    public TblArticleDTO(int articleId, String posterEmail, String title, String description, String image, Timestamp date, String status) {
	this.articleId = articleId;
	this.posterEmail = posterEmail;
	this.title = title;
	this.description = description;
	this.image = image;
	this.date = date;
	this.status = status;
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
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
	this.image = image;
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
     * @return the status
     */
    public String getStatus() {
	return status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * @return the posterEmail
     */
    public String getposterEmail() {
	return posterEmail;
    }

    /**
     * @param posterEmail the posterEmail to set
     */
    public void setposterEmail(String posterEmail) {
	this.posterEmail = posterEmail;
    }
    
}
