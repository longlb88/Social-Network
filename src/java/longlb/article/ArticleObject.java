/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.article;

import java.io.Serializable;
import longlb.tblarticle.TblArticleDTO;

/**
 *
 * @author Long Le
 */
public class ArticleObject implements Serializable{
    private TblArticleDTO articleDTO;
    private String userName;

    public ArticleObject() {
    }

    public ArticleObject(TblArticleDTO articleDTO, String userName) {
	this.articleDTO = articleDTO;
	this.userName = userName;
    }

    /**
     * @return the articleDTO
     */
    public TblArticleDTO getArticleDTO() {
	return articleDTO;
    }

    /**
     * @param articleDTO the articleDTO to set
     */
    public void setArticleDTO(TblArticleDTO articleDTO) {
	this.articleDTO = articleDTO;
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
