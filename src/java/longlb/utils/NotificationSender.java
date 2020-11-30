/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.utils;

import java.sql.SQLException;
import javax.naming.NamingException;
import longlb.tblnotification.TblNotificationDAO;

/**
 *
 * @author Long Le
 */
public class NotificationSender {
    
    /**
     * send notification to article owner
     * @param articleId id of article
     * @param email email of user who invoke notify
     * @param content content of notification
     * @param notificationType "R" for reaction, "C" for comment
     * @throws SQLException
     * @throws NamingException 
     */
    public static void sendNotification(int articleId, String email, String content, String notificationType) throws SQLException, NamingException{	
	TblNotificationDAO notificationDAO = new TblNotificationDAO();
	
	int notificationId = notificationDAO.checkExistedNotification(articleId, notificationType); 
	if (notificationId != 0){ 
	    //if existed, update notification
	    notificationDAO.updateNotification(notificationId, email, content);
	} else { 
	    //if not existed, create notification
	    notificationDAO.createNotification(articleId, email, notificationType, content);
	}
    }
}
