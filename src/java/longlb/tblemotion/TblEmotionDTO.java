/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.tblemotion;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Long Le
 */
public class TblEmotionDTO implements Serializable{
    private int emotionId;
    private int articleId;
    private String reactorEmail;
    private String emotion;
    private String status;
    private Timestamp date;

    public TblEmotionDTO() {
    }

    public TblEmotionDTO(int emotionId, int articleId, String reactorEmail, String emotion, String status, Timestamp date) {
	this.emotionId = emotionId;
	this.articleId = articleId;
	this.reactorEmail = reactorEmail;
	this.emotion = emotion;
	this.status = status;
	this.date = date;
    }

    

    /**
     * @return the emotionId
     */
    public int getEmotionId() {
	return emotionId;
    }

    /**
     * @param emotionId the emotionId to set
     */
    public void setEmotionId(int emotionId) {
	this.emotionId = emotionId;
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
     * @return the reactorEmail
     */
    public String getReactorEmail() {
	return reactorEmail;
    }

    /**
     * @param email the reactorEmail to set
     */
    public void setReactorEmail(String email) {
	this.reactorEmail = email;
    }

    /**
     * @return the emotion
     */
    public String getEmotion() {
	return emotion;
    }

    /**
     * @param emotion the emotion to set
     */
    public void setEmotion(String emotion) {
	this.emotion = emotion;
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
    
    
}
