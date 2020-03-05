import com.era.logger.LoggerUtility;
import com.era.pushernotifications.PusherPushNotificationsManager;
import com.era.pushernotifications.data.PushDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author PC
 */
@TestMethodOrder(OrderAnnotation.class)
public class PusherPushNotificationsManagerJUnitTest  {
    
    @Test
    @Order(1)
    public void updateCompanyHttpClientTest() {
        
        try{
            
            //Iinit pusher notifications
            final PusherPushNotificationsManager PusherPushNotificationsManager = new PusherPushNotificationsManager();
            PusherPushNotificationsManager.setIPushNotificationMessageSubscriber((PushDataModel PushDataModel) -> {

                try{
                    
                    LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: Received PushDataModel " + PushDataModel);
                    
                    Assertions.assertTrue(true);
                                        
                }catch(Exception e){
                    LoggerUtility.getSingleton().logError(PusherPushNotificationsManagerJUnitTest.class, e);                
                }
            });
            PusherPushNotificationsManager.connect();
            
            //Never ends this program
            Thread.currentThread().join();        
            
        }catch(Exception e){
            LoggerUtility.getSingleton().logError(PusherPushNotificationsManagerJUnitTest.class, e);
            
            Assertions.fail();
        }
    }
}
